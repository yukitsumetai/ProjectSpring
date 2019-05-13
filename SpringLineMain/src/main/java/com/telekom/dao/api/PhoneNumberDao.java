package com.telekom.dao.api;

import java.math.BigInteger;


public interface PhoneNumberDao extends PaginationDao {
     void deleteNumber(BigInteger number);
}




