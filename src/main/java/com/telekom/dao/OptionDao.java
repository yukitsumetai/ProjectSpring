package com.telekom.dao;

import com.telekom.entity.Option;

import java.util.List;

public interface OptionDao extends PaginationDao {

    List getAll();

    List<Option> findByTariffParents(Integer tariffId);

    void add(Option option);

    Option getOne(Integer id);

    List getAllValidNoParent();
    List getAllNoParent();
    List getAllValidNoChildrenAndParent();
    List getAllNoChildrenAndParent();
    List getAllValid();
    List getAllValidNoParentNoGroup();
    List<Option> findByTariffChildren(Integer id);

}

