package com.telekom.dao.api;

import com.telekom.model.entity.PhoneNumber;

import java.math.BigInteger;
import java.util.List;



public interface PhoneNumberDao extends PaginationDao {
     void deleteNumber(BigInteger number);
}




