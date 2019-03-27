package com.telekom.dao;

import com.telekom.entity.Tariff;

import java.util.List;

public interface TariffDao extends PaginationDao {

    List<Tariff> getAll();

    List<Tariff> getAllValid();

    void add(Tariff tariff);

    Tariff getOne(Integer id);


}

