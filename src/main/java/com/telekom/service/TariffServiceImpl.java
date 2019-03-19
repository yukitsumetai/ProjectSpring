package com.telekom.service;


import com.telekom.dao.OptionDao;
import com.telekom.dao.TariffDao;
import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.TariffDTO;
import com.telekom.mapper.TariffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service



public class TariffServiceImpl implements TariffService {


    @Autowired
    private TariffDao tariffDao;
    @Autowired
    private OptionDao optionDao;

    @Autowired
    private TariffMapper tariffMapper;

    @Override
    @Transactional
    public List<TariffDTO> getAll() {

        List<Tariff> tariffs= tariffDao.getAll();
        List<TariffDTO> tariffsDTO=new ArrayList<>();
        for (Tariff t:tariffs) {

            tariffsDTO.add(tariffMapper.EntityToDto(t));
        }
        return tariffsDTO;
    }


    @Override
    @Transactional
    public TariffDTO getOne(int id) {

        Tariff t=tariffDao.getOne(id);
        return tariffMapper.EntityToDto(t);

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
    @Transactional
    public void editTariff(TariffDTO t, List<Integer> opts){
        Tariff tmp= tariffMapper.DtoToEntity(t);
        for (Integer id:opts
        ) {
            tmp.addOption(optionDao.getOne(id));
        }
        tariffDao.editTariff(tmp);
    }

    @Override
    @Transactional
    public void editTariff(TariffDTO t){
        Tariff tmp= tariffMapper.DtoToEntity(t);
        tariffDao.editTariff(tmp);
    }

    @Override
    @Transactional
    public void deleteTariff(Integer id) {
        tariffDao.deleteTariff(id);
    }

    }

