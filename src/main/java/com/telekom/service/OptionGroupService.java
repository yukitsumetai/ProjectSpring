package com.telekom.service;

import com.telekom.entity.OptionGroup;
import com.telekom.entityDTO.OptionGroupDTO;

import java.util.List;

public interface OptionGroupService {
    OptionGroupDTO getOne(int id);
    void add(OptionGroupDTO optionGroup);
    void editOptionGroup(OptionGroupDTO t);
    void deleteOptionGroup(Integer id);
    List<OptionGroupDTO> getAll();
    List<OptionGroupDTO> getAllValid();
    void SetOptions(OptionGroupDTO optionGroup, List<Integer> id);
}
