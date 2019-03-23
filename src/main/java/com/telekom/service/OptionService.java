package com.telekom.service;

import com.telekom.entity.Client;
import com.telekom.entity.Option;
import com.telekom.entityDTO.OptionDTO;

import java.util.List;
import java.util.Set;

public interface OptionService {

   List<OptionDTO> getAll();
   List<OptionDTO> getAllNoParent();
   List<OptionDTO> getAllNoChildrenAndParent();
   Set<OptionDTO> findByTariff(Integer tariffId);
   void add(OptionDTO option);
   OptionDTO getOne(int id);
   void editOption(OptionDTO option);
   void deleteOption(Integer id);

}

