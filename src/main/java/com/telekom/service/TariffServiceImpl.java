package com.telekom.service;


import com.telekom.dao.ContractDao;
import com.telekom.dao.OptionDao;
import com.telekom.dao.TariffDao;
import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
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
    private ContractDao contractDao;

    @Autowired
    private TariffMapper tariffMapper;

    @Override //УДАЛИТЬ!
    public Boolean checkContracts(Integer id) {
        if (contractDao.getByTariff(id) != null) return false;
        else return true;
    }

    @Override
    @Transactional
    public List<TariffDTO> getAll() {

        List<Tariff> tariffs = tariffDao.getAll();
        List<TariffDTO> tariffsDTO = new ArrayList<>();
        for (Tariff t : tariffs) {

            tariffsDTO.add(tariffMapper.EntityToDto(t));
        }
        return tariffsDTO;
    }


    @Override
    @Transactional
    public List<TariffDTO> getAllValid() {

        List<Tariff> tariffs = tariffDao.getAllValid();
        List<TariffDTO> tariffsDTO = new ArrayList<>();
        for (Tariff t : tariffs) {

            tariffsDTO.add(tariffMapper.EntityToDto(t));
        }
        return tariffsDTO;
    }

    @Override
    @Transactional
    public TariffDTO getOne(int id) {

        Tariff t = tariffDao.getOne(id);
        return tariffMapper.EntityToDto(t);

    }

    @Override
    @Transactional
    public void add(TariffDTO tariff, List<Integer> opts) {
        Tariff t = tariffMapper.DtoToEntity(tariff);
        if (opts != null) {
            for (Integer i : opts) {
                t.addOption(optionDao.getOne(i));
            }
        }
        tariffDao.add(t);
    }

    @Override
    @Transactional
    public void editTariff(TariffDTO t, List<Integer> opts) {
        Tariff tariff=tariffDao.getOne(t.getId());
        tariff.setIsValid(t.isIsValid());
        tariff.setPrice(t.getPrice());
        tariff.setDescription(t.getDescription());
        if (opts != null) {
            for (Integer id : opts
            ) {
                tariff.addOption(optionDao.getOne(id));
            }
        }
    }

    @Override
    @Transactional
    public void editTariff(TariffDTO t) {
        Tariff tariff=tariffDao.getOne(t.getId());
        tariff.setIsValid(t.isIsValid());
        tariff.setPrice(t.getPrice());
        tariff.setDescription(t.getDescription());
    }

    @Override
    @Transactional
    public void deleteTariff(Integer id) {
        Tariff tariff = tariffDao.getOne(id);
        tariff.setIsValid(false);
    }

}

