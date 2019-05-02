package com.telekom.controller;


import com.telekom.model.entity.PhoneNumber;
import com.telekom.model.dto.*;
import com.telekom.service.api.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
public class SharedRestController {
    private static final int GROUPS_PER_PAGE = 5;
    @Autowired
    OptionGroupService optionGroupService;

    @Autowired
    PhoneNumberService phoneNumberService;

    @Autowired
    OptionService optionService;

    @Autowired
    TariffService tariffService;

    @Autowired
    ClientService clientService;

    @Autowired
    private Logger logger;

    @GetMapping(value = "/phoneNumbers")
    public List<PhoneNumber> pagePhoneNumber(@RequestParam Integer page) {

        Page<PhoneNumber> resultPage = phoneNumberService.getOptions(GROUPS_PER_PAGE, 1);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage.getData();
    }

    @GetMapping(value = "/options/optionPages")
    public Page<OptionDto> pageOption(@RequestParam Integer page, @RequestParam(required = false) Integer id,
                                      @RequestParam(required = false) Boolean parent, @RequestParam(required = false) Integer optionId,
                                      @RequestParam(required = false) Boolean group) {
       logger.info("Get Options Controller id: "+id+", parent: "+parent+", optionId: "+optionId+", group: "+group);
        Page<OptionDto> resultPage;
        if (id != null) {
            if (group != null) resultPage = optionService.getOptionsForExistingOptionGroup(GROUPS_PER_PAGE, page, id);
            else resultPage = optionService.getOptionsForTariff(GROUPS_PER_PAGE, page, id);
        } else if (parent !=null) {
            if (optionId == null) resultPage = optionService.getOptionsForNewOption(GROUPS_PER_PAGE, page, parent);
            else resultPage = optionService.getPageForExisting(GROUPS_PER_PAGE, page, parent, optionId);
        } else if (group != null) resultPage = optionService.getOpionsForOptionGroup(GROUPS_PER_PAGE, page);
        else resultPage = optionService.getOptions(GROUPS_PER_PAGE, page);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage;
    }

    @GetMapping(value = "/tariffs/tariffPages")
    public Page<TariffDto> pageTariff(@RequestParam Integer page, @RequestParam(required = false) Integer id, @RequestParam(required = false) Boolean parent) {
        logger.info("Get Tariffs Controller id: "+id+", parent: "+parent+", optionId: ");
        Page<TariffDto> resultPage;
        if (id != null) resultPage = tariffService.getAllPaginated(GROUPS_PER_PAGE, page, id);
        else if (parent) resultPage = tariffService.getValidPaginated(GROUPS_PER_PAGE, page);
        else resultPage = tariffService.getAllPaginated(GROUPS_PER_PAGE, page);
        return resultPage;
    }
    @GetMapping(value = "/tariffs/promoted")
    public List<TariffDto> tariffsPromoted() {
        List<TariffDto> resultPage = tariffService.getAllPromoted();
        return resultPage;
    }
    @GetMapping(value = "/clients/clientPages")
    public Page<ClientDto> pageClient(@RequestParam Integer page) {
        return clientService.getPage(GROUPS_PER_PAGE, page);
    }

    @GetMapping(value = "/clients/search")
    public ClientDto pageClient(@RequestParam String phoneNumber) {
        return clientService.getOne(phoneNumber);
    }


    @GetMapping(value = "/newContract/client/check")
    public Boolean pageTariff(@RequestParam Integer passport, @RequestParam String email) {
        return clientService.checkExisting(email, passport);
    }


    @GetMapping(value = "/test")
    public List<OptionGroupDto> findPaginated(@RequestParam Integer page) {

        Page<OptionGroupDto> resultPage = optionGroupService.getOptions(GROUPS_PER_PAGE, page);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage.getData();
    }

    @PostMapping("/captureImage")
    public ClientDto performOCR(@RequestParam(name = "imageprev") String image) {
        ClientDto client = clientService.performOcr(image);
        return client;
    }
}

