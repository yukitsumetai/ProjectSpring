package com.telekom.service;

import com.telekom.entity.OptionGroup;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.OptionGroupDTO;
import com.telekom.entityDTO.Page;

import java.util.List;
import java.util.Set;

public interface OptionGroupService extends Pagination{
    OptionGroupDTO getOne(int id);
    void add(OptionGroupDTO optionGroup);
    void editOptionGroup(OptionGroupDTO t);
    void deleteOptionGroup(Integer id);
    List<OptionGroupDTO> getAll();
    List<OptionGroupDTO> getAllValid();
    void SetOptions(OptionGroupDTO optionGroup, List<Integer> id);
    Page<OptionGroupDTO> getPage(Integer size, Integer page);
    List<OptionGroupDTO> getByName(String name);
    Set<OptionGroupDTO> findByTariff(Integer id);

}
