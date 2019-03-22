package com.telekom.dao;

import com.telekom.entity.Contract;
import com.telekom.entity.Tariff;

import java.math.BigInteger;
import java.util.List;

public interface ContractDao {
    void add(Contract contract);

    Contract getOne(BigInteger n);

    void update(Contract contract);

    List<Contract> getByTariff(Integer id);
}
