package com.telekom.service.api;

import com.telekom.model.dto.OptionGroupDto;
import com.telekom.model.dto.Page;

import java.util.List;
import java.util.Set;

public interface OptionGroupService extends Pagination {
    OptionGroupDto getOne(int id);
    void add(OptionGroupDto optionGroup);
    void editOptionGroup(OptionGroupDto t);
    void deleteOptionGroup(Integer id);
    List<OptionGroupDto> getAll();
    List<OptionGroupDto> getAllValid();
    void setOptionsDto(OptionGroupDto optionGroup, List<Integer> id);
    List<OptionGroupDto> getByName(String name);
    Set<OptionGroupDto> findByTariff(Integer id);

}
