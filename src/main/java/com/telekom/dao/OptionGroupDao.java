package com.telekom.dao;

import com.telekom.entity.OptionGroup;
import com.telekom.entityDTO.OptionGroupDTO;

import java.util.List;

public interface OptionGroupDao {
    List<OptionGroup> getAll();

    List<OptionGroup> getAllValid();

    void add(OptionGroup optionGroup);
    public List<OptionGroup> findByName(String name);

    OptionGroup getOne(Integer id);
    List<OptionGroup> getPages(Integer size, Integer page);
    Long getPagesCount();

}
