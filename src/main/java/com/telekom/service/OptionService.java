package com.telekom.service;

import com.telekom.entity.Client;
import com.telekom.entity.Option;
import com.telekom.entityDTO.OptionDTO;

import java.util.List;

public interface OptionService {

   List<OptionDTO> getAll();

   void add(Option option, List<Integer> opts);
   void add(Option option);
   OptionDTO getOne(int id);
   void editOption(Option option);

   void deleteOption(Integer id);

}

