package com.telekom.service;

import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.TariffDTO;

import java.util.List;

public interface TariffService {

    List<TariffDTO> getAll();
    List<TariffDTO> getAllValid();

    void add(TariffDTO tariff, List<Integer> opts);


    TariffDTO getOne(int id);
    Boolean checkContracts(Integer id);

    void editTariff(TariffDTO tariffDto);
    void editTariff(TariffDTO t, List<Integer> opts);
    void deleteTariff(Integer id);
   // void update(OptionDTO option, Integer id);

}

