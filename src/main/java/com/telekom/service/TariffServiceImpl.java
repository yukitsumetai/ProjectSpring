package com.telekom.service;


import com.telekom.entity.Tariff;
import com.telekom.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service

@Transactional(readOnly = true)

public class TariffServiceImpl implements TariffService {


    @Autowired

    private TariffRepository tariffRepository;


    @Override
    public List<Tariff> getAll() {
        return tariffRepository.findAll();
    }

    @Override
    @Transactional
    public void add(Tariff tariff) {
        tariffRepository.save(tariff);
    }

    @Override
    public Tariff getOne(String name) {
        return tariffRepository.findByName(name);

    }


}