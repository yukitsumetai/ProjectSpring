package com.telekom.service;

import com.telekom.dao.ClientDAO;
import com.telekom.entity.Client;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service



public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDAO clientDao;

    @Override
    public List<Client> getAll() {
        return clientDao.getAll();
    }

    @Override
    @Transactional
    public void add(Client client) {
        clientDao.add(client);
    }

    @Override
    public Client getOne(String email) {
        return clientDao.getOne(email);
    }


}