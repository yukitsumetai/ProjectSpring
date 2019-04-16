package com.telekom.service.impl;

import com.telekom.dao.ClientDAO;
import com.telekom.model.entity.Client;

import com.telekom.model.dto.ClientDto;
import com.telekom.model.dto.OptionGroupDto;
import com.telekom.model.dto.Page;
import com.telekom.mapper.ClientMapper;
import com.telekom.service.api.ClientService;
import com.telekom.service.api.Pagination;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class ClientServiceImpl extends PaginationImpl<ClientDto> implements ClientService {

    @Autowired
    private ClientDAO clientDao;
    @Autowired
    private ClientMapper clientMapper;

    @Override
    @Transactional
    public Page<ClientDto> getPage(Integer size, Integer page) {
        List<ClientDto> pageGroups = new ArrayList<>();
        for (Client c : clientDao.getPages(size, page)
        ) {
            pageGroups.add(clientMapper.entityToDto(c));
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
    public void add(ClientDto client) {
        Client tmp = clientMapper.dtoToEntity(client);
        clientDao.add(tmp);
    }

    @Transactional
    public ClientDto getOne(String number) {
        BigInteger number2 = new BigInteger(number);
        ClientDto tmp;
        try{
         tmp = clientMapper.entityToDto(clientDao.getOne(number2));}
        catch (NullPointerException e){
            tmp=null;
        }
        return tmp;
    }


    @Transactional
    public ClientDto getOne(Long id) {
        return  clientMapper.entityToDto(clientDao.getOne(id));
    }



    public ClientDto getOne(Integer id) {
        Client tmp=clientDao.getOne(id);
        return clientMapper.entityToDtoWithoutContract(tmp);
    }


}