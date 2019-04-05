package com.telekom.service;

import com.telekom.dao.*;
import com.telekom.entity.*;
import com.telekom.entityDTO.ContractDTO;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.OptionGroupDTO;
import com.telekom.entityDTO.TariffDTO;
import com.telekom.mapper.ClientMapper;
import com.telekom.mapper.ContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private PhoneNumberDao phoneNumberDao;
    @Autowired
    private ClientDAO clientDao;
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

    @Override
    @Transactional
    public void add(ContractDTO contract) {

        Contract tmp = contractMapper.DtoToEntity(contract);
        phoneNumberDao.deleteNumber(tmp.getPhoneNumber());
        Client c = clientDao.getOne(contract.getClient().getId());
        if (c == null) {
            c = clientMapper.DtoToEntity(contract.getClient());
            c.setContract(tmp);
            User user = new User(tmp, passwordEncoder.encode(contract.getClient().getPassword()));
            c.setUser(user);
            clientDao.add(c);
        } else {
            User user = userDao.getOne(c.getUser().getId());
            user.addContract(tmp);
        }
        contractDao.add(tmp);
        tmp.setClient(c);
    }

    @Override
    public Set<OptionDTO> getParentsForExisting(ContractDTO contract) {
        Set<OptionDTO> options = getOptions(contract);
        Set<OptionDTO> existing = contract.getOptions();
        for (OptionDTO o : existing) {
            if (o.getParent() == null) {
                setExisting(options, o);
            }
        }
        return options;
    }


    @Override
    public Set<OptionDTO> getChildrenForExisting(ContractDTO contract) {
        Set<OptionDTO> existing = contract.getOptions();
        Set<OptionDTO> children = getOptionsChildren(contract);
        for (OptionDTO o : existing) {
            if (o.getParent() != null) {
                setExisting(children, o);
            }
        }
        return children;
    }


    private void setExisting(Set<OptionDTO> target, OptionDTO existing) {
        boolean flag = true;
        for (OptionDTO c : target) {
            if (c.getId() == existing.getId()) {
                flag = false;
                c.setExisting(true);
                c.setPriceOneTime(0.00);
            }
        }
        if (flag) {
            existing.setExisting(true);
            existing.setPriceOneTime(0.00);
            target.add(existing);
        }

    }

    @Override
    public List<TariffDTO> getTariffsForAdd(ContractDTO contract) {
        Integer id = contract.getTariff().getId();
        List<TariffDTO> tariffs = new ArrayList<>(tariffService.getAllValid());
        tariffs.removeIf(st -> st.getId() == id);
        return tariffs;
    }


    @Override
    public boolean setOptions(ContractDTO contract, List<Integer> id, boolean existing) {
        Set<OptionDTO> temporary = new HashSet<>();

        if (id != null) {
            contract.setPriceOneTime(0.00);
            contract.setPrice(0.00);
            contract.addPrice(contract.getTariff().getPrice());
            for (Integer i : id) {
                OptionDTO tmp = optionService.getOne(i);
                if (tmp != null) { //0 exist
                    temporary.add(tmp);
                    contract.addPrice(tmp.getPriceMonthly());
                    if (contract.getOptions() != null) {
                        for (OptionDTO op : contract.getOptions()
                        ) {
                            if (op.getId() == tmp.getId()) tmp.setPriceOneTime(0.00); //if option already existed
                        }
                    }
                    contract.addPriceOneTime(tmp.getPriceOneTime());
                }
            }
            contract.setOptions(temporary);
            if(!existing) return optionValidation(contract);
        }
        else {
            contract.setOptions(new HashSet<>());
            contract.setPrice(0.00);
            contract.addPrice(contract.getTariff().getPrice());
        }
        return true;
    }

    @Override
    public Set<OptionDTO> getOptions(ContractDTO contract) {
        return optionService.findByTariff(contract.getTariff().getId());
    }

    public boolean optionValidation(ContractDTO contract) {
        if (basicOptionValidation(contract)) {
            if (ChildrenOptionValidation(contract)) return incompatibleOptionValidation(contract);
        }
        return false;
    }

    private boolean basicOptionValidation(ContractDTO contract) {
        for (OptionDTO o : contract.getOptions()) {
            //if (o.isIsValid() == false) return false;

                boolean flag = false;

                for (OptionDTO o2 : contract.getTariff().getOptions()) {
                    if (o2.getId() == o.getId()) flag = true;
                }
                if (!flag) return false;
        }
        return true;
    }


    private boolean ChildrenOptionValidation(ContractDTO contract) {
        for (OptionDTO o : contract.getOptions()) {
            if (o.getParent() != null) {
                boolean flag = false;
                for (OptionDTO o2 : contract.getOptions()) {
                    if (o.getParent().getId() == o2.getId()) flag = true;
                }
                if (!flag) return false;
            }
        }
        return true;
    }

    private boolean incompatibleOptionValidation(ContractDTO contract) {
        if (contract.getOptions().size() > 0) {
            Set<OptionGroupDTO> og = new HashSet<>();
            for (OptionDTO o : contract.getOptions()
            ) {
                if (o.getOptionGroup() != null) {
                    og.add(o.getOptionGroup());
                }
            }
            for (OptionGroupDTO og2 : og
            ) {
                int count = 0;
                for (OptionDTO o : contract.getOptions()) {
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
    public Set<OptionDTO> getOptionsChildren(ContractDTO contract) {
        return optionService.findByTariffChildren(contract.getTariff().getId());
    }

    @Override
    public boolean setTariff(ContractDTO contract, Integer id) {
        TariffDTO tmp = tariffService.getOne(id);
        if (!tmp.isIsValid()) return false;
        contract.setTariff(tmp);
        contract.setPrice(0.0);
        contract.addPrice(tmp.getPrice());
        return true;
    }

    @Override
    public Set<OptionGroupDTO> getGroups(ContractDTO contract) {
        return optionGroupService.findByTariff(contract.getTariff().getId());
    }

    @Override
    @Transactional
    public void update(ContractDTO contractDto) {
        Contract contract = contractDao.getOne(contractDto.getPhoneNumberInt());
        if (contract.getTariff().getId() != contractDto.getTariff().getId()) {
            contract.setOptions(null);
            Tariff t = tariffDao.getOne(contractDto.getTariff().getId());
            contract.setTariff(t);
        } else {
            contract.setOptions(new HashSet<>());
            if (!contractDto.getOptions().isEmpty()) {
                for (OptionDTO o : contractDto.getOptions()
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
    public ContractDTO getOne(String number) {
        BigInteger number2 = new BigInteger(number);
        ContractDTO tmp;
        try {
            tmp = contractMapper.EntityToDto(contractDao.getOne(number2));
        } catch (NullPointerException e) {
            tmp = null;
        }
        return tmp;
    }

    @Override
    @Transactional
    public void block(ContractDTO contract, boolean admin) {
        Contract tmp = contractDao.getOne(contract.getPhoneNumberInt());
        tmp.setBlocked(true);
        if (admin) {
            tmp.setAgentBlock(true);
        }
    }

    @Override
    @Transactional
    public void unblock(ContractDTO contract, boolean admin) {
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
}



