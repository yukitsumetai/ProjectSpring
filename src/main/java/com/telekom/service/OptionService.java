package com.telekom.service;

import com.telekom.entity.Client;
import com.telekom.entity.Option;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.TariffDTO;

import java.util.List;
import java.util.Set;

public interface OptionService {

   List<OptionDTO> getAll();
   List<OptionDTO> getAllNoParent();
   List<OptionDTO> getAllNoParentInvalid(); //includes invalid options for administration
   List<OptionDTO> getAllNoChildrenAndParent();
   List<OptionDTO> getAllNoChildrenParentInvalid(); //includes invalid options for administration
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
   List<OptionDTO> getAllNoParentNoGroup();
   Set<OptionDTO> findByTariffChildren(Integer id);

}

