package com.telekom.service;


import com.telekom.dao.OptionDao;
import com.telekom.dao.TariffDao;
import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class OptionServiceImpl implements OptionService {


    @Autowired
    private OptionDao optionDao;
    @Autowired
    private TariffDao tariffDao;


    @Override
    public List<Option> getAll() {
        return optionDao.getAll();
    }

    @Override
    @Transactional
    public void add(Option option, List<Integer> opts) {
        optionDao.add(option);
        for (Integer i: opts) {
            Tariff tmp=tariffDao.getOne(i);
            tmp.addOption(option);
            tariffDao.add(tmp);
        }
    }

    @Override
    @Transactional
    public void add(Option option) {
        optionDao.add(option);
    }

    @Override
    public Option getOne(int id) {
        return optionDao.getOne(id);

    }


}