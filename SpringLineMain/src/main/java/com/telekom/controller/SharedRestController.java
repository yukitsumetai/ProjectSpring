package com.telekom.controller;


import com.telekom.model.entity.FreePhoneNumber;
import com.telekom.model.dto.*;
import com.telekom.service.api.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private Logger logger;

    /**
     * Groups of phones for dropdown. Ajax request
     * @param page
     * @return
     */
    @GetMapping(value = "/phoneNumbers")
    public List<FreePhoneNumber> pagePhoneNumber(@RequestParam Integer page) {

        Page<FreePhoneNumber> resultPage = phoneNumberService.getPage(GROUPS_PER_PAGE, 1);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage.getData();
    }

    /**
     * Groups of options for paginated table. Ajax request
     * @param page
     * @param id
     * @param parent
     * @param optionId
     * @param group
     * @return
     */
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
        else resultPage = optionService.getPage(GROUPS_PER_PAGE, page);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage;
    }

    /**
     * Groups of tariffs for paginated table. Ajax request
     * @param page
     * @param id
     * @param parent if true returns only valid  tariffs for contracts
     * @return
     */
    @GetMapping(value = "/tariffPages")
    public Page<TariffDto> pageTariff(@RequestParam Integer page, @RequestParam(required = false) Integer id, @RequestParam(required = false) Boolean parent) {
        logger.info("Get Tariffs Controller id: "+id+", parent: "+parent+", optionId: ");
        Page<TariffDto> resultPage;
        if (id != null) resultPage = tariffService.getPage(GROUPS_PER_PAGE, page, id);
        else if (parent) resultPage = tariffService.getValidPaginated(GROUPS_PER_PAGE, page);
        else resultPage = tariffService.getPage(GROUPS_PER_PAGE, page);
        return resultPage;
    }

    /**
     * List of promoted tariffs for advertisment stand
     * @return
     */
    @GetMapping(value = "/promotedTariffs")
    public List<TariffDto> tariffsPromoted() {
        List<TariffDto> resultPage = tariffService.getAllPromoted();
        return resultPage;
    }

    /**
     * Groups of clients for paginated table
     * @param page
     * @return
     */
    @GetMapping(value = "/clients/clientPages")
    public Page<ClientDto> pageClient(@RequestParam Integer page) {
        return clientService.getPage(GROUPS_PER_PAGE, page);
    }

    /**
     * Returns clients by phone number
     * @param phoneNumber
     * @return
     */
    @GetMapping(value = "/clients/search")
    public ClientDto pageClient(@RequestParam String phoneNumber) {
        return clientService.getClient(phoneNumber);
    }

    /**
     * Returns true if client with same mail and/or passport exists
     * @param passport
     * @param email
     * @return
     */
    @GetMapping(value = "/newContract/client/check")
    public Boolean pageTariff(@RequestParam Integer passport, @RequestParam String email) {
        return clientService.checkExisting(email, passport);
    }


    @GetMapping(value = "/test")
    public List<OptionGroupDto> findPaginated(@RequestParam Integer page) {

        Page<OptionGroupDto> resultPage = optionGroupService.getPage(GROUPS_PER_PAGE, page);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage.getData();
    }

    /**
     * Performs OCR from id
     * @param image path to image
     * @param id
     * @return
     */
    @PostMapping("/captureImage")
    public ClientDto performOCR(@RequestParam(name = "imageprev") String image, @RequestParam(name = "contract") String id) {
       logger.info("/captureImage");
        id = id.substring(id.lastIndexOf('@') + 1);
        logger.info("/captureImage2");
        ClientDto client = clientService.performOcr(image, id);
        return client;
    }
}

