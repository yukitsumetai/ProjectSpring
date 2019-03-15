package com.telekom.dao;
import com.telekom.entity.Tariff;

import java.util.List;

public interface TariffDao {

    List<Tariff> getAll();

    void add(Tariff tariff);

    Tariff getOne(Integer id);

    void editTariff(Tariff tariff);

    void deleteProduct(Integer id);
}

