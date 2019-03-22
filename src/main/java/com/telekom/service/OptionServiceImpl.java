package com.telekom.service;


import com.telekom.dao.OptionDao;
import com.telekom.dao.TariffDao;
import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.mapper.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional(readOnly = true)
public class OptionServiceImpl implements OptionService {


    @Autowired
    private OptionDao optionDao;
    @Autowired
    private TariffDao tariffDao;
    @Autowired
    private OptionMapper optionMapper;

    @Override
    public List<OptionDTO> getAll() {

        List<Option> Options = optionDao.getAll();
        List<OptionDTO> OptionsDTO = new ArrayList<>();
        for (Option t : Options) {
            OptionsDTO.add(optionMapper.EntityToDto(t));
        }
        return OptionsDTO;
    }

    @Override
    public Set<OptionDTO> findByTariff(Integer id) {

        List<Option> Options = optionDao.findByTariff(id);
        Set<OptionDTO> OptionsDTO = new HashSet<>();
        for (Option t : Options) {
            OptionsDTO.add(optionMapper.EntityToDto(t));
        }
        return OptionsDTO;
    }

    @Override
    @Transactional
    public void add(OptionDTO option, List<Integer> opts) {
        Option o=optionMapper.DtoToEntity(option);
        optionDao.add(o);
        if (opts != null) {
            for (Integer i : opts) {
                Tariff tmp = tariffDao.getOne(i);
                tmp.addOption(o);
                tariffDao.add(tmp);
            }
        }
    }


    @Override
    public OptionDTO getOne(int id) {
        Option t = optionDao.getOne(id);
        return optionMapper.EntityToDto(t);
    }

    @Override
    @Transactional
    public void editOption(OptionDTO o) {
        Option option=optionDao.getOne(o.getId());
        option.setValid(o.isIsValid());
        option.setDescription(o.getDescription());
    }

    @Override
    @Transactional
    public void deleteOption(Integer id) {
        Option option= optionDao.getOne(id);
        option.setValid(false);
    }


}