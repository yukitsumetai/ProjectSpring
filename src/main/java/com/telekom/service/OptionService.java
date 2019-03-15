package com.telekom.service;

import com.telekom.entity.Client;
import com.telekom.entity.Option;

import java.util.List;

public interface OptionService {

   List<Option> getAll();

   void add(Option option, List<Integer> opts);
   void add(Option option);
   Option getOne(int id);

}

