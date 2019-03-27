package com.telekom.service;

import com.telekom.entity.PhoneNumber;

import java.math.BigInteger;
import java.util.List;

public interface PhoneNumberService extends Pagination {
    List<String> getAll();
}

