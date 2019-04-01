package com.telekom.service;

import com.telekom.entity.Client;
import com.telekom.entity.Option;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.Page;
import com.telekom.entityDTO.TariffDTO;

import java.util.List;
import java.util.Set;

public interface OptionService extends Pagination {

   List<OptionDTO> getAll();
   List<OptionDTO> getAll(TariffDTO tariff);
   Set<OptionDTO> findByTariff(Integer tariffId);
   void add(OptionDTO option);
   OptionDTO getOne(int id);
   void editOption(OptionDTO option);
   void deleteOption(Integer id);
   void SetCompatibleTariffs(OptionDTO option, List<Integer> id);
   void removeRelations(OptionDTO option);
   void SetChildren(OptionDTO option, List<Integer> id);
   void SetParent(OptionDTO option, Integer id);
   List<OptionDTO> getAllValid();
   void SetOptionGroup(OptionDTO option, Integer groupId);
   Set<OptionDTO> findByTariffChildren(Integer id);
   Page<OptionDTO> getPage(Integer size, Integer page, Integer tariffId);
    Page<OptionDTO> getPageForNew(Integer size, Integer page, boolean parent);
    Page<OptionDTO> getPageForExisting(Integer size, Integer page, boolean parent, Integer optionId);
   Page<OptionDTO> getPageForGroup(Integer size, Integer page);
}

