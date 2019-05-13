package com.telekom.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "free_phone_numbers")
public class FreePhoneNumber implements Serializable {

    @Id
    private BigInteger phoneNumber ;

    public BigInteger getNumber() {
        return phoneNumber;
    }

    public void setNumber(BigInteger number) {
        this.phoneNumber = number;
    }




}
