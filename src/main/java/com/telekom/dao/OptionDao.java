package com.telekom.dao;

import com.telekom.entity.Option;

import java.util.List;
import java.util.Set;

public interface OptionDao {

    List<Option> getAll();

    List<Option> findByTariff(Integer tariffId);

    void add(Option option);

    Option getOne(Integer id);


}

