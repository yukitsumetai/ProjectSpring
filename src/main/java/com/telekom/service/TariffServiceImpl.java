package com.telekom.service;


import com.telekom.dao.ContractDao;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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



    private List<TariffDTO> listEntityToDto(List<Tariff> tariffs){
        List<TariffDTO> tariffsDTO = new ArrayList<>();
        for (Tariff t : tariffs) {

            tariffsDTO.add(tariffMapper.EntityToDto(t));
        }
        return tariffsDTO;
    }

    @Override
    @Transactional
    public List<TariffDTO> getAll() {

        List<Tariff> tariffs = tariffDao.getAll();
        List<TariffDTO> tariffsDTO = new ArrayList<>();
        for (Tariff t : tariffs) {

            tariffsDTO.add(tariffMapper.EntityToDto(t));
        }
        return listEntityToDto( tariffs);
    }

    @Override
    @Transactional
    public List<TariffDTO> getAllValid() {
        List<Tariff> tariffs = tariffDao.getAllValid();
        return listEntityToDto( tariffs);
    }

    @Override
    public void SetOptions(TariffDTO tariff, List<Integer> id) {
        Set<OptionDTO> options = new HashSet<>();
        if (id != null) {
            for (Integer i : id) {
                OptionDTO t = new OptionDTO();
                t.setId(i);
                options.add(t);
            }
            tariff.setOptions(options);
        }
        else tariff.setOptions(new HashSet<>());
    }



    @Override
    @Transactional
    public TariffDTO getOne(int id) {
        Tariff t = tariffDao.getOne(id);
        return tariffMapper.EntityToDto(t);
    }

    private void addOptions(TariffDTO tariff, Tariff t) {
        if (tariff.getOptions().size() > 0) {
            Set<OptionDTO> options = tariff.getOptions();
            for (OptionDTO o : options
            ) {
                Option tmp = optionDao.getOne(o.getId());
                if (tmp != null) t.addOption(tmp);
            }
        }
    }

    @Override
    @Transactional
    public void add(TariffDTO tariff) {
        Tariff t = tariffMapper.DtoToEntity(tariff);
        addOptions(tariff, t);
        tariffDao.add(t);
    }


    @Override
    @Transactional
    public void editTariff(TariffDTO t) {
        Tariff tariff = tariffDao.getOne(t.getId());
        tariff.setIsValid(t.isIsValid());
        tariff.setDescription(t.getDescription());
        tariff.setOptions(new HashSet<>());
        addOptions(t, tariff);
    }


    @Override
    @Transactional
    public void deleteTariff(Integer id) {
        Tariff tariff = tariffDao.getOne(id);
        tariff.setIsValid(false);
    }

}

