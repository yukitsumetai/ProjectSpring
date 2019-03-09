package com.telekom.service;

import com.telekom.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    void add(Client client);

    Client getOne(String email);

}

