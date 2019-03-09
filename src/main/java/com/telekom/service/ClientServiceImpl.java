package com.telekom.service;

import com.telekom.entity.Client;
import com.telekom.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service

@Transactional(readOnly = true)

public class ClientServiceImpl implements ClientService {


    @Autowired

    private ClientRepository clientRepository;


    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional
    public void add(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client getOne(String email) {
        return clientRepository.findByEmail(email);

    }


}