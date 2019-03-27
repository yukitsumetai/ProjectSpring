package com.telekom.dao;

import com.telekom.entity.Option;

import java.util.List;

public interface OptionDao extends PaginationDao {

    List<Option> getAll();

    List<Option> findByTariffParents(Integer tariffId);

    void add(Option option);

    Option getOne(Integer id);

    List<Option> getAllValidNoParent();
    List<Option> getAllNoParent();
    List<Option> getAllValidNoChildrenAndParent();
    List<Option> getAllNoChildrenAndParent();
    List<Option> getAllValid();
    List<Option> getAllValidNoParentNoGroup();
    List<Option> findByTariffChildren(Integer id);

}

