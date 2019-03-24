package com.telekom.dao;

import com.telekom.entity.OptionGroup;

import java.util.List;

public interface OptionGroupDao {
    List<OptionGroup> getAll();

    List<OptionGroup> getAllValid();

    void add(OptionGroup optionGroup);

    OptionGroup getOne(Integer id);



}
