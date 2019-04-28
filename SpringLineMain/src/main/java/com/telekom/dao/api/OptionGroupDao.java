package com.telekom.dao.api;

import com.telekom.model.entity.OptionGroup;

import java.util.List;

public interface OptionGroupDao extends PaginationDao {
    List<OptionGroup> getAll();

    List<OptionGroup> getAllValid();

    void add(OptionGroup optionGroup);
    List<OptionGroup> findByName(String name);
    List<OptionGroup> findByTariff(Integer id);
    OptionGroup getOne(Integer id);
    List<OptionGroup> getPages(Integer size, Integer page);
    Long getPagesCount();

}
