package com.telekom.dao;

import com.telekom.entity.Option;

import java.util.List;
import java.util.Set;

public interface OptionDao {

    List<Option> getAll();

    List<Option> findByTariffParents(Integer tariffId);

    void add(Option option);

    Option getOne(Integer id);

    List<Option> getAllValidNoParent();

    List<Option> getAllValidNoChildrenAndParent();

    List<Option> getAllValid();

    List<Option> getAllValidNoParentNoGroup();
    List<Option> findByTariffChildren(Integer id);

}

