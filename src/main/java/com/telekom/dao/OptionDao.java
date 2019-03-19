package com.telekom.dao;

import com.telekom.entity.Option;

import java.util.List;

public interface OptionDao {

    List<Option> getAll();

    void add(Option option);
    Option getOne(Integer id);
    void editOption(Option option);
    void deleteOption(Integer id);

}

