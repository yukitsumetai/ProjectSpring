package com.telekom.service;


import com.telekom.entity.PhoneNumber;
import com.telekom.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Service

public class PhoneNumberServiceImpl implements PhoneNumberService {


    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Override
    public List<PhoneNumber> getAll() {
        return phoneNumberRepository.findAll();
    }

    @Override
    public PhoneNumber getOne(BigInteger phoneNumber) {
        return phoneNumberRepository.findByPhoneNumber(phoneNumber);

    }

    public List<String> getNumbers() {
        List<String> tmp=new ArrayList<>();
        for (PhoneNumber n: phoneNumberRepository.findAll()) {
           String s="+"+n.getNumber();
           tmp.add(s);
        }
        return tmp;
    }

}