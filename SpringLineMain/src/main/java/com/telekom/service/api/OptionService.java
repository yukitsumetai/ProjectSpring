package com.telekom.service.api;

import com.telekom.model.dto.OptionDto;
import com.telekom.model.dto.Page;
import com.telekom.model.dto.TariffDto;

import java.util.List;
import java.util.Set;

public interface OptionService extends Pagination {

    List<OptionDto> getAll();

    List<OptionDto> getAll(TariffDto tariff);

    Set<OptionDto> findByTariff(Integer tariffId);

    void add(OptionDto option);

    OptionDto getOne(int id);

    void editOption(OptionDto option);

    void deleteOption(Integer id);

    void setCompatibleTariffs(OptionDto option, List<Integer> id);

    void removeRelations(OptionDto option);

    void setChildren(OptionDto option, List<Integer> id);

    void setParent(OptionDto option, Integer id);

    List<OptionDto> getAllValid();

    void setOptionGroup(OptionDto option, Integer groupId);

    Set<OptionDto> findByTariffChildren(Integer id);

    Page<OptionDto> getPage(Integer size, Integer page, Integer tariffId);

    Page<OptionDto> getPageForNew(Integer size, Integer page, boolean parent);

    Page<OptionDto> getPageForExisting(Integer size, Integer page, boolean parent, Integer optionId);

    Page<OptionDto> getPageForGroup(Integer size, Integer page);

    Page<OptionDto> getPageForExistingGroup(Integer size, Integer page, Integer optionGroupId);
}

