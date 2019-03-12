package com.telekom.service;

import com.telekom.entity.PhoneNumber;
import java.math.BigInteger;
import java.util.List;

public interface PhoneNumberService {

    List<PhoneNumber> getAll();
    PhoneNumber getOne(BigInteger phoneNumber);
    List<String> getNumbers();

}

