package com.telekom.service;

import com.telekom.dao.ClientDAO;
import com.telekom.entity.Client;

import com.telekom.entityDTO.ClientDTO;
import com.telekom.entityDTO.Page;
import com.telekom.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Service
public class ClientServiceImpl extends PaginationImpl<ClientDTO> implements ClientService {

    @Autowired
    private ClientDAO clientDao;
    @Autowired
    private ClientMapper clientMapper;

    @Override
    @Transactional
    public Page<ClientDTO> getPage(Integer size, Integer page) {
        List<ClientDTO> pageGroups = new ArrayList<>();
        for (Client c : clientDao.getPages(size, page)
        ) {
            pageGroups.add(clientMapper.EntityToDto(c));
        }
        Long total=clientDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    @Override
    public Boolean checkExisting(String email, Integer passport){
        Boolean isEmail=clientDao.getOneByEmail(email);
        Boolean isPassport=clientDao.getOneByPassport(passport);
        return isEmail || isPassport;
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
        ClientDTO tmp;
        try{
         tmp = clientMapper.EntityToDto(clientDao.getOne(number2));}
        catch (NullPointerException e){
            tmp=null;
        }
        return tmp;
    }


    @Transactional
    public ClientDTO getOne(Long id) {
        return  clientMapper.EntityToDto(clientDao.getOne(id));
    }



    public ClientDTO getOne(Integer id) {
        Client tmp=clientDao.getOne(id);
        return clientMapper.EntityToDtoWithoutContract(tmp);
    }




}