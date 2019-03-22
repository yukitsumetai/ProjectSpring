package com.telekom.service;

import com.telekom.dao.*;
import com.telekom.entity.Client;
import com.telekom.entity.Contract;
import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.ContractDTO;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.TariffDTO;
import com.telekom.mapper.ClientMapper;
import com.telekom.mapper.ContractMapper;
import jdk.nashorn.internal.runtime.options.Options;
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
    private TariffService tariffService;
    @Autowired
    private OptionDao optionDao;
    @Autowired
    private TariffDao tariffDao;

    @Override
    @Transactional
    public void add(ContractDTO contract) {

        Contract tmp = contractMapper.DtoToEntity(contract);
        phoneNumberDao.deleteNumber(tmp.getPhoneNumber());
        Client c = clientDao.getOne(contract.getClient().getId());
        if (c == null) {
            c = clientMapper.DtoToEntity(contract.getClient());
            c.setContract(tmp);
            clientDao.add(c);
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
    public List<TariffDTO> getTariffsForAdd(ContractDTO contract) {
        Integer id = contract.getTariff().getId();
        List<TariffDTO> tariffs = new ArrayList<>(tariffService.getAllValid());
        tariffs.removeIf(st -> st.getId() == id);
        return tariffs;
    }


    public void update(ContractDTO contract) {
        Contract tmp = contractMapper.DtoToEntity(contract);
        tmp.setClient(clientDao.getOne(contract.getClient().getId()));
        contractDao.update(tmp);
    }

    @Override
    public void setOptions(ContractDTO contract, List<Integer> id) {
        for (Integer i : id
        ) {
            OptionDTO tmp = optionService.getOne(i);
            contract.addOption(tmp);
            contract.setPrice(tmp.getPriceMonthly());
            contract.setPriceOneTime(tmp.getPriceOneTime());
        }

    }

    @Override
    public List<OptionDTO> setTariff(ContractDTO contract, Integer id) {
        TariffDTO tmp = tariffService.getOne(id);
        contract.setTariff(tmp);
        contract.setPrice(0.0);
        contract.setPriceOneTime(0.0);
        contract.setPrice(tmp.getPrice());
        return tmp.getOptions();
    }

    @Override
    @Transactional
    public void setTariffAndUpdate(ContractDTO contractDto, Integer id) {
       Contract contract= contractDao.getOne(contractDto.getPhoneNumberInt());
        Tariff t = tariffDao.getOne(id);
        contract.setTariff(t);
        contract.setPrice(0.0);
        contract.setPriceOneTime(0.0);
        contract.setPrice(t.getPrice());
    }

    @Override
    @Transactional
    public void setOptionsAndUpdate(ContractDTO contract, List<Integer> id) {
        Contract tmp = contractDao.getOne(contract.getPhoneNumberInt());
        for (Integer i : id
        ) {
            Option tmp2 = optionDao.getOne(i);
            tmp.addOption(tmp2);
            contract.setPrice(tmp2.getPriceMonthly());
            contract.setPriceOneTime(tmp2.getPriceOneTime());
        }
        //setOptions(contract, id);
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
    public void deleteOption(ContractDTO contract, Integer id) {
        BigInteger n = new BigInteger(contract.getPhoneNumber());
        Contract tmp = contractDao.getOne(n);
        tmp.deleteOption(id);
    }
}


