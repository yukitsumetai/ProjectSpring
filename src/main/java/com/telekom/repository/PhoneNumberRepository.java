package com.telekom.repository;

import com.telekom.entity.Client;
import com.telekom.entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {

    PhoneNumber findByPhoneNumber(BigInteger phoneNumber);


}
