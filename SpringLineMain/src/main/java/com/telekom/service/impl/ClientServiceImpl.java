package com.telekom.service.impl;

import com.telekom.dao.api.ClientDao;
import com.telekom.model.entity.Client;

import com.telekom.model.dto.ClientDto;
import com.telekom.model.dto.Page;
import com.telekom.mapper.ClientMapper;
import com.telekom.service.api.ClientService;
import com.telekom.utils.api.ImageRecognitionService;
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
    private ClientDao clientDao;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    ImageRecognitionService imageRecognition;
    @Autowired
    Logger logger;

    /**
     * Returns a page of clients Dto (eg for table)
     * @param size page size
     * @param page number of page
     * @return page of clients Dto with list of option Dto and page parameters
     */
    @Override
    @Transactional
    public Page<ClientDto> getPage(Integer size, Integer page) {
        logger.info("Getting clients, page " + page);
        List<ClientDto> pageGroups = new ArrayList<>();
        List<Client> clients = clientDao.getPages(size, page);
        if (clients != null) {
            for (Client c : clientDao.getPages(size, page)
            ) {
                pageGroups.add(clientMapper.entityToDto(c));
            }
        }
        Long total = clientDao.getPagesCount();
        return getPageDraft(pageGroups, total, page, size);
    }

    /**
     * Checking if there is a client with same email and/or passport
     * @param email
     * @param passport
     * @return true if exists, false otherwise
     */
    @Override
    public Boolean checkExisting(String email, Integer passport) {
        logger.info("Searching for client, email " + email + ", passport " + passport);
        Boolean isEmail = clientDao.getOneByEmail(email);
        Boolean isPassport = clientDao.getOneByPassport(passport);
        return isEmail || isPassport;
    }

    /**
     * Adds new client to DB
     * @param client
     */
    @Override
    @Transactional
    public void add(ClientDto client) {
        logger.info("Adding new client");
        Client tmp = clientMapper.dtoToEntity(client);
        clientDao.add(tmp);
    }

    /**
     * Returns client by phone number
     * @param number phone number
     * @return client Dto
     */
    @Transactional
    public ClientDto getClient(String number) {
        logger.info("Searching for client by phone number " + number);
        BigInteger number2 = new BigInteger(number);
        ClientDto tmp = clientMapper.entityToDto(clientDao.getOne(number2));
        return tmp;
    }

    /**
     * Returns client by user id
     * @param id user id
     * @return client Dto
     */
    @Transactional
    public ClientDto getClient(Long id) {
        logger.info("Searching for client by user id" + id);
        return clientMapper.entityToDto(clientDao.getOne(id));
    }

    /**
     * Returns client by client id
     * @param id id of a client
     * @return client Dto
     */
    public ClientDto getClient(Integer id) {
        logger.info("Searching for client by id " + id);
        Client tmp = clientDao.getOne(id);
        return clientMapper.entityToDtoWithoutContract(tmp);
    }

    /**
     * Saves image if id, performs OCR and then deletes it
     * to recognize some customer data instead of writng it manually
     * @param image path to base64 image
     * @param id contract id
     * @return Client dto with recognized fields ()
     */
    @Override
    public ClientDto performOcr(String image, String id) {
        logger.info("Saving Image");
        ClientDto client = new ClientDto();
        image = image.substring(image.lastIndexOf(',') + 1);
        byte[] decodedBytes = Base64.getDecoder().decode(image);

        String pathName = id + ".jpeg";
        File file = new File(pathName);
        try{
        String absolute = file.getCanonicalPath();
        logger.info("Canonical " +absolute);}
        catch (IOException e){}
        String absolute2 = file.getAbsolutePath();
        logger.info("Absolute " +absolute2);
        logger.info("Image was created at "+pathName);
        try {
            FileUtils.writeByteArrayToFile(file, decodedBytes);
            logger.info("Performing OCR");
            imageRecognition.doOCR(client, absolute2);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
       // file.delete();
        return client;
    }

}