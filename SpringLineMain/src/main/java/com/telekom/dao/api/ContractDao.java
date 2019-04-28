package com.telekom.dao.api;

import com.telekom.model.entity.Contract;

import java.math.BigInteger;

public interface ContractDao {

    void add(Contract contract);

    Contract getOne(BigInteger n);

    void update(Contract contract);
}
