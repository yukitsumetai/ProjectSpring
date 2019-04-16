package com.telekom.service.api;

import com.telekom.model.dto.Page;
import com.telekom.model.dto.TariffDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TariffService {

    List<TariffDto> getAll();

    List<TariffDto> getAllValid();

    void add(TariffDto tariff);

    TariffDto getOne(int id);

    void editTariff(TariffDto tariffDto);

    void deleteTariff(Integer id);

    void setOptions(TariffDto tariff, List<Integer> id);

    Page<TariffDto> getPage(Integer size, Integer page, Integer optionId);

    Page<TariffDto> getPage(Integer size, Integer page);

    Page<TariffDto> getPageValid(Integer size, Integer page);
}

