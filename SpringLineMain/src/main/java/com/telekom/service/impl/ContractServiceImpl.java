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

    /**
     * Sets tariff to a contract Dto
     * @param contract
     * @param id id of a tariff
     * @return true if tariff was successfuly set. Else false
     */
    @Override
    public boolean setTariff(ContractDto contract, Integer id) {
        logger.info("Setting tariff " + id + " to contractDto ");
        TariffDto tmp = tariffService.getTariff(id);
        if (!tmp.isIsValid()) return false;
        contract.setTariff(tmp);
        contract.setPrice(0.0);
        contract.addPrice(tmp.getPrice());
        return true;
    }

    /**
     * Returns option groups that have options compatible with contract's tariff
     * @param contract
     * @return set of option groups Dto
     */
    @Override
    public Set<OptionGroupDto> getGroups(ContractDto contract) {
        return optionGroupService.findByTariff(contract.getTariff().getId());
    }

    /**
     * Returns parent options that are compatible with contract's tariff
     * @param contract
     * @return set of options Dto
     */
    @Override
    public Set<OptionDto> getOptionsParents(ContractDto contract) {
        return optionService.findByTariff(contract.getTariff().getId());
    }

    /**
     * Returns children options that are compatible with contract's tariff
     * @param contract
     * @return set of options Dto
     */
    @Override
    public Set<OptionDto> getOptionsChildren(ContractDto contract) {
        return optionService.findByTariffChildren(contract.getTariff().getId());
    }

    /**
     * Sets option to contract Dto and contract price accordingly
     * @param contract
     * @param id list of options' ids
     * @param existing contract existing or not. If existing contract has options their one time price equals zero
     * @return true if options were set successfully else otherwise
     */
    @Override
    public boolean setOptions(ContractDto contract, List<Integer> id, boolean existing) {
        logger.info("Setting options");
        Set<OptionDto> temporary = new HashSet<>();
        contract.setPrice(0.00);
        contract.addPrice(contract.getTariff().getPrice());
        if (id != null) {
            logger.info("Adding options to contract");
            contract.setPriceOneTime(0.00);
            for (Integer i : id) {
                OptionDto tmp = optionService.getOne(i);
                if (tmp != null) {
                    temporary.add(tmp);
                    contract.addPrice(tmp.getPriceMonthly());
                    existingOptionsRemoveOneTimePrice(contract, tmp);
                    contract.addPriceOneTime(tmp.getPriceOneTime());
                }
            }
        }
        contract.setOptions(temporary);
        if (!existing) return optionValidation(contract);
        return true;
    }

    /**
     * Removes one time price of already exiting options in contract
     * @param contract
     * @param tmp
     */
    public void existingOptionsRemoveOneTimePrice(ContractDto contract, OptionDto tmp) {
        if (contract.getOptions() != null) {
            for (OptionDto op : contract.getOptions()
            ) {
                if (op.getId() == tmp.getId()) tmp.setPriceOneTime(0.00); //if option already existed OneTime price=0
            }
        }
    }

    /**
     * Returns list of valid tariffs without current one
     * @param contract
     * @return list tariff Dto
     */
    @Override
    public List<TariffDto> getTariffsForAdd(ContractDto contract) {
        List<TariffDto> tariffs = new ArrayList<>(tariffService.getAllValid());
        int id = contract.getTariff().getId();
        tariffs.removeIf(st -> st.getId() == id);
        return tariffs;
    }

    /**
     * validates if contract options are compatible
     * @param contract
     * @return
     */
    private boolean optionValidation(ContractDto contract) {
        if (basicOptionValidation(contract) && childrenOptionValidation(contract))
             return incompatibleOptionValidation(contract);
        return false;
    }

    /**
     * Validates if options are valid and compatible with tariff
     * @param contract
     * @return
     */
    private boolean basicOptionValidation(ContractDto contract) {
        for (OptionDto o : contract.getOptions()) {
            if (o.isIsValid() == false) return false;

            boolean flag = false;

            for (OptionDto o2 : contract.getTariff().getOptions()) {
                if (o2.getId() == o.getId()) flag = true;
            }
            if (!flag) return false;
        }
        return true;
    }

    /**
     * Validates if option relations are valid
     * @param contract
     * @return
     */
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

    /**
     * Validates that there are no incompatible options
     * @param contract
     * @return
     */
    private boolean incompatibleOptionValidation(ContractDto contract) {
        if (!contract.getOptions().isEmpty()) {
            Set<OptionGroupDto> og = new HashSet<>();
            gettingOptionGroups(contract, og);
            for (OptionGroupDto og2 : og
            ) {
                int count = 0;
                for (OptionDto o : contract.getOptions()) {
                    if (o.getOptionGroup() != null && o.getOptionGroup().getId() == og2.getId()) count++;
                }
                if (count >= 2) return false;
            }
        }
        return true;
    }

    private void gettingOptionGroups(ContractDto contract, Set<OptionGroupDto> og){
        for (OptionDto o : contract.getOptions()
        ) {
            if (o.getOptionGroup() != null) {
                og.add(o.getOptionGroup());
            }
        }
    }

    /**
     * Returns contract by phone number
     * @param number phone number
     * @return contract Dto
     */
    @Override
    @Transactional
    public ContractDto getOne(String number) {
        logger.info("Searching for contract" + number);
        BigInteger number2 = new BigInteger(number);
        Contract contract=contractDao.getOne(number2);
        if (contract!=null){
            return contractMapper.entityToDto(contract);
        }
        else logger.info("Contract not found " + number2);
        return null;
    }

    /**
     * Blocks contract that other operations are not valid
     * @param contract
     * @param admin contract was blocked by admin or not
     */
    @Override
    @Transactional
    public void block(ContractDto contract, boolean admin) {
        logger.info("Blocking contract" + contract.getPhoneNumberInt() + " by admin " + admin);
        Contract tmp = contractDao.getOne(contract.getPhoneNumberInt());
        tmp.setBlocked(true);
        if (admin) {
            tmp.setAgentBlock(true);
        }
    }

    /**
     * Unblocks blocked contract
     * @param contract
     * @param admin contract whants to unblock admin or not
     */
    @Override
    @Transactional
    public void unblock(ContractDto contract, boolean admin) {
        logger.info("Unblocking contract" + contract.getPhoneNumberInt() + " by admin " + admin);
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

    /**
     * Returns parent options for existing cotract marking already existing options
     * @param contract
     * @return
     */
    @Override
    public Set<OptionDto> getParentsForExistingContract(ContractDto contract) {
        logger.info("Getting parents for existing contracts " + contract.getPhoneNumberInt());
       Set<OptionDto> existing = contract.getOptions();
        Set<OptionDto> parents = getOptionsParents(contract);
        if (parents==null) parents = new HashSet<>();
        for (OptionDto o : existing) {
            if (o.getParent() == null) {
                setExisting(parents, o);
            }
        }
        return parents;
    }

    /**
     * Returns children options for existing cotract marking already existing options
     * @param contract
     * @return
     */
    @Override
    public Set<OptionDto> getChildrenForExistingContract(ContractDto contract) {
        logger.info("Getting children for existing contracts " + contract.getPhoneNumberInt());
        Set<OptionDto> existing = contract.getOptions();
        Set<OptionDto> children = getOptionsChildren(contract);
        if (children==null) children = new HashSet<>();
        children.addAll(getOptionsChildren(contract));
        for (OptionDto o : existing) {
            if (o.getParent() != null) {
                setExisting(children, o);
            }
        }
        return children;
    }

    /**
     * Attaching/marking existing option to/in target list of options. Setting its one time price as 0
     * @param target target list of options
     * @param existing existing option
     */
    private void setExisting(Set<OptionDto> target, OptionDto existing) {
        boolean notInList = true;
        //marking option if its in the list
        for (OptionDto c : target) {
            if (c.getId() == existing.getId()) {
                notInList = false;
                c.setExisting(true);
                c.setPriceOneTime(0.00);
            }
        }
        //addin option if its not in the list
        if (notInList) {
            existing.setExisting(true);
            existing.setPriceOneTime(0.00);
            target.add(existing);
        }

    }

    /**
     * Adds contract to DB
     * @param contract
     */
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

    /**
     * Updates existing contract in DB
     * @param contractDto
     */
    @Override
    @Transactional
    public void update(ContractDto contractDto) {
        logger.info("Updating contract " + contractDto.getPhoneNumberInt());
        Contract contract = contractDao.getOne(contractDto.getPhoneNumberInt());
        Integer tariffId = contractDto.getTariff().getId();
        if (contract.getTariff().getId() != tariffId) {
            logger.info("Updating tariff to " + tariffId);
            contract.setOptions(new HashSet<>());
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

    /**
     * Sends email with invoice to customer
     * @param newClient boolean if client new -Welcome email, if Existing - update email
     * @param contract
     * @return
     */
    @Override
    public boolean sendEmail(Boolean newClient, ContractDto contract) {
        return  mailSender.sendMessageWithAttachment(newClient, contract);
    }


}



