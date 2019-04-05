package com.telekom.dao;

import com.telekom.entity.Contract;

import java.math.BigInteger;

public interface ContractDao {

    void add(Contract contract);

    Contract getOne(BigInteger n);

    void update(Contract contract);
}
