package com.telekom.service;

import com.telekom.entity.Option;
import com.telekom.entity.Tariff;

import java.util.List;

public interface TariffService {

    List<Tariff> getAll();

    void add(Tariff tariff, List<Integer> opts);

    Tariff getOne(int id);

    void editTariff(Tariff tariff);

   // void update(OptionDTO option, Integer id);

}

