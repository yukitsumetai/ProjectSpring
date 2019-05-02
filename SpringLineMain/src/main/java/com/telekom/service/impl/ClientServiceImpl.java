package com.telekom.service.impl;

import com.telekom.dao.api.ClientDAO;
import com.telekom.model.entity.Client;

import com.telekom.model.dto.ClientDto;
import com.telekom.model.dto.Page;
import com.telekom.mapper.ClientMapper;
import com.telekom.service.api.ClientService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class ClientServiceImpl extends PaginationImpl<ClientDto> implements ClientService {

    @Autowired
    private ClientDAO clientDao;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    ImageRecognitionImpl imageRecognition;
    @Autowired
    Logger logger;

    @Override
    @Transactional
    public Page<ClientDto> getPage(Integer size, Integer page) {
        logger.info("Getting clients, page "+page);
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
        logger.info("Searching for client, email "+email+", passport "+passport);
        Boolean isEmail=clientDao.getOneByEmail(email);
        Boolean isPassport=clientDao.getOneByPassport(passport);
        return isEmail || isPassport;
    }

    @Override
    @Transactional
    public void add(ClientDto client) {
        logger.info("Adding new client");
        Client tmp = clientMapper.dtoToEntity(client);
        clientDao.add(tmp);
    }

    @Transactional
    public ClientDto getOne(String number) {
        logger.info("Searching for client by phone number " +number );
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
        logger.info("Searching for client by user id" +id );
        return  clientMapper.entityToDto(clientDao.getOne(id));
    }



    public ClientDto getOne(Integer id) {
        logger.info("Searching for client by id " +id);
        Client tmp=clientDao.getOne(id);
        return clientMapper.entityToDtoWithoutContract(tmp);
    }

    @Override
    public ClientDto performOcr(String image){
        logger.info("Saving Image");
        image= image.substring(image.lastIndexOf(",") +1);
        byte[] decodedBytes = Base64.getDecoder().decode(image);
        String pathName="C:\\Users\\ekochuro\\IdeaProjects\\ProjectSpring\\pass3.jpeg";
        try{
            FileUtils.writeByteArrayToFile(new File(pathName), decodedBytes);}
        catch (IOException e){
            logger.info("Exception", e);
        }
        logger.info("Performing OCR");
        ClientDto client = new ClientDto();
        imageRecognition.doOCR(client, pathName);
        return client;
    }



}