package com.telekom.dao;
import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import org.springframework.stereotype.Component;

import java.util.List;

public interface TariffDao {

    List<Tariff> getAll();

    void add(Tariff tariff);

    Tariff getOne(Integer id);

    void editTariff(Tariff tariff);

    void deleteTariff(Integer id);
}

