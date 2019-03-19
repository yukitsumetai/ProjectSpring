package com.telekom.service;


import com.telekom.dao.PhoneNumberDao;
import com.telekom.entity.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Service

public class PhoneNumberServiceImpl implements PhoneNumberService {


    @Autowired
    private PhoneNumberDao phoneNumberDao;

    @Override
    public List<String> getAll() {
        List<String> numbers=new ArrayList<>();
        for (PhoneNumber n:
        phoneNumberDao.getAll()) {
            numbers.add(n.getNumber().toString());
        }
        return numbers;
    }





}