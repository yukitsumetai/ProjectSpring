package com.telekom.service.api;

import com.telekom.model.dto.OptionGroupDto;

import java.util.List;
import java.util.Set;

public interface OptionGroupService extends Pagination {
    OptionGroupDto getOptionGroup(int id);
    void add(OptionGroupDto optionGroup);
    void editOptionGroup(OptionGroupDto t);
    void deleteOptionGroup(Integer id);
    List<OptionGroupDto> getAll();

    void setOptionsDto(OptionGroupDto optionGroup, List<Integer> id);

    Set<OptionGroupDto> findByTariff(Integer id);

}
