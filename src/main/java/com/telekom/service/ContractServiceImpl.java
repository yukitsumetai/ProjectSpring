package com.telekom.service;

import com.telekom.dao.ClientDAO;
import com.telekom.dao.ContractDao;
import com.telekom.dao.PhoneNumberDao;
import com.telekom.entity.Client;
import com.telekom.entity.Contract;
import com.telekom.entityDTO.ContractDTO;
import com.telekom.mapper.ClientMapper;
import com.telekom.mapper.ContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;

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

    @Override
    @Transactional
    public void add(ContractDTO contract) {

        Contract tmp = contractMapper.DtoToEntity(contract);
       // phoneNumberDao.deleteNumber(tmp.getPhoneNumber());
        Client c =clientDao.getOne(contract.getClient().getId());
        if( c==null) {
           c = clientMapper.DtoToEntity(contract.getClient());
           c.setContract(tmp);
           clientDao.add(c);
       }
        contractDao.add(tmp);
        tmp.setClient(c);
    }

    @Override
    @Transactional
    public ContractDTO getOne(String number) {
        BigInteger number2 = new BigInteger(number);
        ContractDTO tmp = contractMapper.EntityToDto(contractDao.getOne(number2));
        return tmp;
    }
}


