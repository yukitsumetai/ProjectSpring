package com.telekom.service;

import com.telekom.dao.ContractDao;
import com.telekom.dao.PhoneNumberDao;
import com.telekom.entity.Client;
import com.telekom.entity.Contract;
import com.telekom.entityDTO.ContractDTO;
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
    private ContractDao contractDao;
    @Autowired
    private PhoneNumberDao phoneNumberDao;


    @Override
    @Transactional
    public void add(ContractDTO contract) {
        Contract tmp = contractMapper.DtoToEntity(contract);
        phoneNumberDao.deleteNumber(tmp.getClient().getPhoneNumber());
       Client c= tmp.getClient();
       c.setContract(tmp);
        contractDao.add(tmp);
    }

    @Override
    public ContractDTO getOne(String number) {
        BigInteger number2 = new BigInteger(number);
        ContractDTO tmp = contractMapper.EntityToDto(contractDao.getOne(number2));
        return tmp;
    }
}


