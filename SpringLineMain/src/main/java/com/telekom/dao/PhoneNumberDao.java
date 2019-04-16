package com.telekom.dao;

import com.telekom.model.entity.PhoneNumber;

import java.math.BigInteger;
import java.util.List;



public interface PhoneNumberDao extends PaginationDao {
     List<PhoneNumber> getAll();
     void deleteNumber(BigInteger number);
}




