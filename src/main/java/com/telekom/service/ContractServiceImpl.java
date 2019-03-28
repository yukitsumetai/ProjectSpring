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

    @Override
    @Transactional
    public void add(ContractDTO contract) {

        Contract tmp = contractMapper.DtoToEntity(contract);
        phoneNumberDao.deleteNumber(tmp.getPhoneNumber());
        Client c = clientDao.getOne(contract.getClient().getId());
        if (c == null) {
            c = clientMapper.DtoToEntity(contract.getClient());
            c.setContract(tmp);
            User user = new User(tmp, contract.getClient().getPassword());
            c.setUser(user);
            clientDao.add(c);
        }
      else {
            User user = userDao.getOne(c.getUser().getId());
            user.addContract(tmp);
        }
        contractDao.add(tmp);
        tmp.setClient(c);
    }


    @Override
    public Set<OptionDTO> getOptionsForAdd(ContractDTO contract) {
        Set<OptionDTO> options = optionService.findByTariff(contract.getTariff().getId());
        if (contract.getOptions() != null) {
            Set<OptionDTO> optionsExisted = new HashSet<>(contract.getOptions());
            for (OptionDTO e : optionsExisted
            ) {
                options.removeIf(st -> st.getId() == e.getId());
            }
        }
        return options;
    }

    @Override
    public Set<OptionDTO> getParentsForExisting(ContractDTO contract) {
        Set<OptionDTO> options = getOptions(contract);
        Set<OptionDTO> existing = contract.getOptions();
        for (OptionDTO o : existing) {
            if (o.getParent() == null) {
                boolean flag = true;
                for (OptionDTO p : options) {
                    if (p.getId() == o.getId()) {
                        flag = false;
                        p.setExisting(true);
                        p.setPriceOneTime(0.00);
                    } }
                if (flag)  {
                    o.setExisting(true);
                    o.setPriceOneTime(0.00);
                   options.add(o);}
            }
        }
        return options;
    }


    @Override
    public Set<OptionDTO> getChildrenForExisting(ContractDTO contract) {
            Set<OptionDTO> existing = contract.getOptions();
            Set<OptionDTO> children =getOptionsChildren(contract);
            for (OptionDTO o : existing) {
                if (o.getParent() != null) {
                    boolean flag = true;
                    for (OptionDTO c : children) {
                        if (c.getId() == o.getId()){
                            flag = false;
                            c.setExisting(true);
                            c.setPriceOneTime(0.00);
                        }
                    }
                    if (flag) {
                        o.setExisting(true);
                        o.setPriceOneTime(0.00);
                        children.add(o);
                    }
                }
            }
            return children;
    }




        @Override
        public List<TariffDTO> getTariffsForAdd (ContractDTO contract){
            Integer id = contract.getTariff().getId();
            List<TariffDTO> tariffs = new ArrayList<>(tariffService.getAllValid());
            tariffs.removeIf(st -> st.getId() == id);
            return tariffs;
        }


        @Override
        public void setOptions (ContractDTO contract, List < Integer > id){

        Set<OptionDTO> temporary = new HashSet<>();

            if (id!=null){
                contract.setPriceOneTime(0.00);
                contract.setPrice(0.00);
                contract.addPrice(contract.getTariff().getPrice());
            for (Integer i : id) {
                OptionDTO tmp = optionService.getOne(i);
                if (tmp != null) { //0 exist
                    temporary.add(tmp);
                    contract.addPrice(tmp.getPriceMonthly());
                    if(contract.getOptions()!=null){
                        for (OptionDTO op:contract.getOptions()
                             ) {
                            if(op.getId()==tmp.getId())tmp.setPriceOneTime(0.00); //if option already existed
                        }
                    }
                    contract.addPriceOneTime(tmp.getPriceOneTime());
                }
            }
            contract.setOptions(temporary);
        }}

        @Override
        public Set<OptionDTO> getOptions (ContractDTO contract){
            return optionService.findByTariff(contract.getTariff().getId());
        }

        public boolean OptionValidation (ContractDTO contract){
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
                            if (o.getOptionGroup().getId() == og2.getId()) {
                                count++;
                            }
                        }
                    }
                    if (count >= 2) return false;
                }
            }
            return true;
        }

        @Override
        public Set<OptionDTO> getOptionsChildren (ContractDTO contract){
            return optionService.findByTariffChildren(contract.getTariff().getId());
        }

        @Override
        public void setTariff (ContractDTO contract, Integer id){
            TariffDTO tmp = tariffService.getOne(id);
            contract.setTariff(tmp);
            contract.setPrice(0.0);
            //contract.setPriceOneTime(0.0);
            contract.addPrice(tmp.getPrice());
        }


        public Set<OptionDTO> getChilds (ContractDTO contract){

            return optionService.findByTariff(contract.getTariff().getId());
        }

        @Override
        public Set<OptionGroupDTO> getGroups (ContractDTO contract){
            Set<OptionGroupDTO> groups = optionGroupService.findByTariff(contract.getTariff().getId());

            return groups;
        }

        @Override
        @Transactional
        public void update (ContractDTO contractDto){
            Contract contract = contractDao.getOne(contractDto.getPhoneNumberInt());
            if (contract.getTariff().getId() != contractDto.getTariff().getId()) {
                contract.setOptions(null);
                Tariff t = tariffDao.getOne(contractDto.getTariff().getId());
                contract.setTariff(t);
                contract.setPrice(0.0);
                contract.setPrice(t.getPrice());
            } else {
                contract.setOptions(new HashSet<>());
                if (contractDto.getOptions().size() > 0) {
                    for (OptionDTO o : contractDto.getOptions()
                    ) {
                        Option tmp2 = optionDao.getOne(o.getId());
                        contract.addOption(tmp2);
                        contract.setPrice(tmp2.getPriceMonthly());
                    }
                }
            }
        }

        @Override
        @Transactional
        public void setOptionsAndUpdate (ContractDTO contract, List < Integer > id){
            Contract tmp = contractDao.getOne(contract.getPhoneNumberInt());
            //setOptions(contract, id);
        }

        @Override
        @Transactional
        public ContractDTO getOne (String number){
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
        public void deleteOption (ContractDTO contract, Integer id){
            BigInteger n = new BigInteger(contract.getPhoneNumber());
            Contract tmp = contractDao.getOne(n);
            tmp.deleteOption(id);
        }
    @Override
    @Transactional
    public void block(ContractDTO contract){
        Contract tmp = contractDao.getOne(contract.getPhoneNumberInt());
       tmp.setBlocked(true);
       tmp.setAgentBlock(true);
    }
    @Override
    @Transactional
    public void unblock(ContractDTO contract){
        Contract tmp = contractDao.getOne(contract.getPhoneNumberInt());
        tmp.setAgentBlock(false);
        tmp.setBlocked(false);

    }

    }


