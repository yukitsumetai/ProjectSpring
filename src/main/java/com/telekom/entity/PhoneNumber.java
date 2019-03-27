package com.telekom.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "free_phone_numbers")
public class PhoneNumber implements Serializable {

    @Id
    BigInteger phoneNumber ;


    public BigInteger getNumber() {
        return phoneNumber;
    }

    public void setNumber(BigInteger number) {
        this.phoneNumber = number;
    }




}
