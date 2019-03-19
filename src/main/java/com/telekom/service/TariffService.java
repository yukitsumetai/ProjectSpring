package com.telekom.service;

import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.TariffDTO;

import java.util.List;

public interface TariffService {

    List<TariffDTO> getAll();

    void add(Tariff tariff, List<Integer> opts);

    void add(Tariff tariff);


    TariffDTO getOne(int id);

    void editTariff(TariffDTO tariffDto);
    void editTariff(TariffDTO t, List<Integer> opts);
    void deleteTariff(Integer id);
   // void update(OptionDTO option, Integer id);

}

