package com.telekom.service;

import com.telekom.entity.Client;
import com.telekom.entityDTO.ClientDTO;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getAll();

    void add(ClientDTO client);

    ClientDTO getOne(String number);

}

