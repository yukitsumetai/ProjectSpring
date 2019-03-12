package com.telekom.entity;


import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "free_phone_numbers")
public class PhoneNumber {

    @Id
    BigInteger phoneNumber ;


    public BigInteger getNumber() {
        return phoneNumber;
    }

    public void setNumber(BigInteger number) {
        this.phoneNumber = number;
    }




}
