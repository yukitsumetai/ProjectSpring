package com.telekom.service;

import com.telekom.dao.ClientDAO;
import com.telekom.entity.Client;

import com.telekom.entityDTO.ClientDTO;
import com.telekom.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDAO clientDao;
    @Autowired
    private ClientMapper clientMapper;

    @Override
    public List<ClientDTO> getAll() {
        List<ClientDTO> clients = new ArrayList<>();
        for (Client c : clientDao.getAll()
        ) {
            clients.add(clientMapper.EntityToDto(c));
        }
        return clients;
    }

    @Override
    @Transactional
    public void add(ClientDTO client) {
        Client tmp = clientMapper.DtoToEntity(client);
        clientDao.add(tmp);
    }


    @Transactional
    public ClientDTO getOne(String number) {
        BigInteger number2 = new BigInteger(number);
        ClientDTO tmp = clientMapper.EntityToDto(clientDao.getOne(number2));
        return tmp;
    }


    public ClientDTO getOne(Integer id) {
        return clientMapper.EntityToDtoWithoutContract(clientDao.getOne(id));
    }


}