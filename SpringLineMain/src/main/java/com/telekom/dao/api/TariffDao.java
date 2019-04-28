package com.telekom.dao.api;

import com.telekom.model.entity.Tariff;

import java.util.List;

public interface TariffDao extends PaginationDao {

    List getAllPromoted();

    List<Tariff> getAllValid();

    void add(Tariff tariff);

    Tariff getOne(Integer id);

    List<Tariff> getPagesValid(Integer size, Integer page);

    Long getPagesValidCount();


}

