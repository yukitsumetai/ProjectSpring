package com.telekom.service.impl;

import com.telekom.dao.api.*;
import com.telekom.model.dto.*;
import com.telekom.mapper.ClientMapper;
import com.telekom.mapper.ContractMapper;
import com.telekom.model.entity.*;
import com.telekom.service.api.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private OptionService optionService;
    @Autowired
    private OptionGroupService optionGroupService;
    @Autowired
    private TariffService tariffService;
    @Autowired
    private OptionDao optionDao;
    @Autowired
    private TariffDao tariffDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private PhoneNumberDao phoneNumberDao;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    PdfCreator pdfCreator;
    @Autowired
    MailService mailSender;

    @Autowired
    Logger logger;


    @Override
    public boolean setTariff(ContractDto contract, Integer id) {
        logger.info("Setting tariff "+id+" to contractDto ");
        TariffDto tmp = tariffService.getTariff(id);
        if (!tmp.isIsValid()) return false;
        contract.setTariff(tmp);
        contract.setPrice(0.0);
        contract.addPrice(tmp.getPrice());
        return true;
    }

    @Override
    public Set<OptionGroupDto> getGroups(ContractDto contract) {
        return optionGroupService.findByTariff(contract.getTariff().getId());
    }

    @Override
    public Set<OptionDto> getOptionsParents(ContractDto contract) {
        return optionService.findByTariff(contract.getTariff().getId());
    }

    @Override
    public Set<OptionDto> getOptionsChildren(ContractDto contract) {
        return optionService.findByTariffChildren(contract.getTariff().getId());
    }

    @Override
    public boolean setOptions(ContractDto contract, List<Integer> id, boolean existing) {
        Set<OptionDto> temporary = new HashSet<>();

        if (id != null) {
            logger.info("Adding options to contract");
            contract.setPriceOneTime(0.00);
            contract.setPrice(0.00);
            contract.addPrice(contract.getTariff().getPrice());
            for (Integer i : id) {
                OptionDto tmp = optionService.getOne(i);
                if (tmp != null) { //0 exist
                    temporary.add(tmp);
                    contract.addPrice(tmp.getPriceMonthly());
                    if (contract.getOptions() != null) {
                        for (OptionDto op : contract.getOptions()
                        ) {
                            if (op.getId() == tmp.getId()) tmp.setPriceOneTime(0.00); //if option already existed OneTime price=0
                        }
                    }
                    contract.addPriceOneTime(tmp.getPriceOneTime());
                }
            }
            contract.setOptions(temporary);
            if (!existing) return optionValidation(contract);
        } else {
            logger.info("Removing options from contract");
            contract.setOptions(temporary);
            contract.setPrice(0.00);
            contract.addPrice(contract.getTariff().getPrice());
        }
        return true;
    }

    @Override
    public List<TariffDto> getTariffsForAdd(ContractDto contract) {
        List<TariffDto> tariffs = new ArrayList<>(tariffService.getAllValid());
        Integer id = contract.getTariff().getId();
        tariffs.removeIf(st -> st.getId() == id);
        return tariffs;
    }

    private boolean optionValidation(ContractDto contract) {
        if (basicOptionValidation(contract)) {
            if (childrenOptionValidation(contract)) return incompatibleOptionValidation(contract);
        }
        return false;
    }

    private boolean basicOptionValidation(ContractDto contract) {
        for (OptionDto o : contract.getOptions()) {
            //if (o.isIsValid() == false) return false;

            boolean flag = false;

            for (OptionDto o2 : contract.getTariff().getOptions()) {
                if (o2.getId() == o.getId()) flag = true;
            }
            if (!flag) return false;
        }
        return true;
    }

    private boolean childrenOptionValidation(ContractDto contract) {
        for (OptionDto o : contract.getOptions()) {
            if (o.getParent() != null) {
                boolean flag = false;
                for (OptionDto o2 : contract.getOptions()) {
                    if (o.getParent().getId() == o2.getId()) flag = true;
                }
                if (!flag) return false;
            }
        }
        return true;
    }

    private boolean incompatibleOptionValidation(ContractDto contract) {
        if (!contract.getOptions().isEmpty()) {
            Set<OptionGroupDto> og = new HashSet<>();
            for (OptionDto o : contract.getOptions()
            ) {
                if (o.getOptionGroup() != null) {
                    og.add(o.getOptionGroup());
                }
            }
            for (OptionGroupDto og2 : og
            ) {
                int count = 0;
                for (OptionDto o : contract.getOptions()) {
                    if (o.getOptionGroup() != null) {
                        if (o.getOptionGroup().getId() == og2.getId()) count++;
                    }
                }
                if (count >= 2) return false;
            }
        }
        return true;
    }


    @Override
    @Transactional
    public void update(ContractDto contractDto) {
        logger.info("Updating contract "+ contractDto.getPhoneNumberInt());
        Contract contract = contractDao.getOne(contractDto.getPhoneNumberInt());
        Integer tariffId=contractDto.getTariff().getId();
        if (contract.getTariff().getId() != tariffId) {
            logger.info("Updating tariff to "+ tariffId);
            contract.setOptions(null);
            Tariff t = tariffDao.getOne(tariffId);
            contract.setTariff(t);
        } else {
            logger.info("Updating options");
            contract.setOptions(new HashSet<>());
            if (!contractDto.getOptions().isEmpty()) {
                for (OptionDto o : contractDto.getOptions()
                ) {
                    Option tmp2 = optionDao.getOne(o.getId());
                    contract.addOption(tmp2);
                }
            }
        }
        contract.setPrice(0.0);
        contract.setPrice(contractDto.getPrice());
    }

    @Override
    @Transactional
    public ContractDto getOne(String number) {
        logger.info("Searching for contract"+number);
        BigInteger number2 = new BigInteger(number);
        ContractDto tmp;
        try {
            tmp = contractMapper.entityToDto(contractDao.getOne(number2));
        } catch (NullPointerException e) {
            logger.info("Contract not found "+number2);
            tmp = null;
        }
        return tmp;
    }



    @Override
    @Transactional
    public void block(ContractDto contract, boolean admin) {
        logger.info("Blocking contract"+contract.getPhoneNumberInt()+" by admin "+admin);
        Contract tmp = contractDao.getOne(contract.getPhoneNumberInt());
        tmp.setBlocked(true);
        if (admin) {
            tmp.setAgentBlock(true);
        }
    }

    @Override
    @Transactional
    public void unblock(ContractDto contract, boolean admin) {
        logger.info("Unblocking contract"+contract.getPhoneNumberInt()+" by admin "+admin);
        Contract tmp = contractDao.getOne(contract.getPhoneNumberInt());
        if (tmp.isAgentBlock()) {
            if (admin) {
                tmp.setAgentBlock(false);
                tmp.setBlocked(false);
            }
        } else {
            tmp.setAgentBlock(false);
            tmp.setBlocked(false);
        }
    }

    @Override
    public Set<OptionDto> getParentsForExistingContract(ContractDto contract) {
        logger.info("Getting parents for existing contracts "+contract.getPhoneNumberInt());
        Set<OptionDto> existing = contract.getOptions();
        Set<OptionDto> parents = getOptionsParents(contract);
        for (OptionDto o : existing) {
            if (o.getParent() == null) {
                setExisting(parents, o);
            }
        }
        return parents;
    }


    @Override
    public Set<OptionDto> getChildrenForExistingContract(ContractDto contract) {
        logger.info("Getting children for existing contracts "+contract.getPhoneNumberInt());
        Set<OptionDto> existing = contract.getOptions();
        Set<OptionDto> children = getOptionsChildren(contract);
        for (OptionDto o : existing) {
            if (o.getParent() != null) {
                setExisting(children, o);
            }
        }
        return children;
    }


    private void setExisting(Set<OptionDto> target, OptionDto existing) {
        boolean notInList = true;
        for (OptionDto c : target) {
            if (c.getId() == existing.getId()) {
                notInList = false;
                c.setExisting(true);
                c.setPriceOneTime(0.00);
            }
        }
        if (notInList) {
            existing.setExisting(true);
            existing.setPriceOneTime(0.00);
            target.add(existing);
        }

    }

    @Override
    @Transactional
    public void add(ContractDto contract) {
        logger.info("Adding new contract");
        Contract tmp = contractMapper.dtoToEntity(contract);
        phoneNumberDao.deleteNumber(tmp.getPhoneNumber());
        Client c = clientDao.getOne(contract.getClient().getId());
        if (c == null) {
            logger.info("Adding new client");
            c = clientMapper.dtoToEntity(contract.getClient());
            c.setContract(tmp);
            User user = new User(tmp, passwordEncoder.encode(contract.getClient().getPassword()));
            c.setUser(user);
            clientDao.add(c);
        } else {
            logger.info("Adding contract to existing client " + c.getUser().getId());
            User user = userDao.getOne(c.getUser().getId());
            user.addContract(tmp);
        }
        contractDao.add(tmp);
        tmp.setClient(c);
    }

    @Override
    public boolean sendPdf(Boolean newClient, ContractDto contract){
        try {
            mailSender.sendMessageWithAttachment(newClient,  contract);
        } catch (MessagingException e) {
            logger.info("Exception", e);
            return false;
        }
        return true;
    }


}



