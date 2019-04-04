package com.telekom.controller;


import com.telekom.entity.PhoneNumber;
import com.telekom.entityDTO.*;
import com.telekom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/phoneNumbers")
    public List<PhoneNumber> pagePhoneNumber(@RequestParam Integer page) {

        Page<PhoneNumber> resultPage = phoneNumberService.getPage(GROUPS_PER_PAGE, 1);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage.getData();
    }

    @GetMapping(value = "/options/optionPages")
    public Page<OptionDTO> pageOption(@RequestParam Integer page, @RequestParam(required = false) Integer id,
                                      @RequestParam(required = false) Boolean parent, @RequestParam(required = false) Integer optionId,
                                      @RequestParam(required = false) Boolean group) {
        Page<OptionDTO> resultPage;
        if (id != null) {
            if (group != null) resultPage = optionService.getPageForExistingGroup(GROUPS_PER_PAGE, page, id);
            else resultPage = optionService.getPage(GROUPS_PER_PAGE, page, id);
        } else if (parent !=null) {
            if (optionId == null) resultPage = optionService.getPageForNew(GROUPS_PER_PAGE, page, parent);
            else resultPage = optionService.getPageForExisting(GROUPS_PER_PAGE, page, parent, optionId);
        } else if (group != null) resultPage = optionService.getPageForGroup(GROUPS_PER_PAGE, page);
        else resultPage = optionService.getPage(GROUPS_PER_PAGE, page);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage;
    }

    @GetMapping(value = "/tariffs/tariffPages")
    public Page<TariffDTO> pageTariff(@RequestParam Integer page, @RequestParam(required = false) Integer id, @RequestParam(required = false) Boolean parent) {
        Page<TariffDTO> resultPage;
        if (id != null) resultPage = tariffService.getPage(GROUPS_PER_PAGE, page, id);
        else if (parent ==true) resultPage = tariffService.getPageValid(GROUPS_PER_PAGE, page);
        else resultPage = tariffService.getPage(GROUPS_PER_PAGE, page);
        return resultPage;
    }

    @GetMapping(value = "/clients/clientPages")
    public Page<ClientDTO> pageClient(@RequestParam Integer page) {
        Page<ClientDTO> resultPage = clientService.getPage(GROUPS_PER_PAGE, page);
        return resultPage;
    }

    @GetMapping(value = "/clients/search")
    public ClientDTO pageClient(@RequestParam String phoneNumber) {
        ClientDTO resultPage = clientService.getOne(phoneNumber);
        return resultPage;
    }


    @GetMapping(value = "/newContract/client/check")
    public Boolean pageTariff(@RequestParam Integer passport, @RequestParam String email) {
        return clientService.checkExisting(email, passport);
    }


    @GetMapping(value = "/search")
    public List<OptionGroupDTO> findByName(Model model, @RequestParam(value = "name", required = false) String name) {

        List<OptionGroupDTO> resultPage = optionGroupService.getByName(name);
        model.addAttribute("optionGroups", resultPage);
        return resultPage;
    }

    @GetMapping(value = "/test")
    public List<OptionGroupDTO> findPaginated(@RequestParam Integer page) {

        Page<OptionGroupDTO> resultPage = optionGroupService.getPage(GROUPS_PER_PAGE, page);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage.getData();
    }


}

