package com.telekom.dao;

import com.telekom.model.entity.Tariff;

import java.util.List;

public interface TariffDao extends PaginationDao {

    List getAll();

    List<Tariff> getAllValid();

    void add(Tariff tariff);

    Tariff getOne(Integer id);

    List<Tariff> getPagesValid(Integer size, Integer page);

    Long getPagesValidCount();


}

