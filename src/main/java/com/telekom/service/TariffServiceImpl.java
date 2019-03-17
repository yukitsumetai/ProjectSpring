package com.telekom.service;


import com.telekom.dao.OptionDao;
import com.telekom.dao.TariffDao;;
import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.TariffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;


@Service



public class TariffServiceImpl implements TariffService {


    @Autowired
    private TariffDao tariffDao;
    @Autowired
    private OptionDao optionDao;

    @Override
    public List<Tariff> getAll() {
        return tariffDao.getAll();
    }


    @Override
    public Tariff getOne(int id) {
        return tariffDao.getOne(id);
    }

    @Override
    @Transactional
    public void add(Tariff tariff, List<Integer> opts) {
        for (Integer i: opts) {
            tariff.addOption(optionDao.getOne(i));
        }
        tariffDao.add(tariff);
    }

    @Override
    @Transactional
    public void add(Tariff tariff) {
        tariffDao.add(tariff);
    }

    @Override
    public void editTariff(Tariff tariff){
        tariffDao.editTariff(tariff);
    }
    @Override
    public void deleteTariff(Integer id) {
        tariffDao.deleteTariff(id);
    }

    }

