package com.telekom.service;

import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.Page;
import com.telekom.entityDTO.TariffDTO;

import java.util.List;

public interface TariffService {

    List<TariffDTO> getAll();
    List<TariffDTO> getAllValid();
    void add(TariffDTO tariff);
    TariffDTO getOne(int id);
    void editTariff(TariffDTO tariffDto);
    void deleteTariff(Integer id);
    void SetOptions(TariffDTO tariff, List<Integer> id);
    Page<TariffDTO> getPage(Integer size, Integer page, Integer optionId);
    Page<TariffDTO> getPage(Integer size, Integer page);


}

