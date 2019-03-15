package com.telekom.service;

import com.telekom.entity.Option;
import com.telekom.entity.Tariff;

import java.util.List;

public interface TariffService {

    List<Tariff> getAll();

    void add(Tariff tariff, List<Integer> opts);

    void add(Tariff tariff);


    Tariff getOne(int id);

    void editTariff(Tariff tariff);

    void deleteTariff(Integer id);
   // void update(OptionDTO option, Integer id);

}

