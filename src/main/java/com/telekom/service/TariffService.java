package com.telekom.service;

import com.telekom.entity.Tariff;

import java.util.List;

public interface TariffService {

    List<Tariff> getAll();

    void add(Tariff tariff);

    Tariff getOne(String name);

}

