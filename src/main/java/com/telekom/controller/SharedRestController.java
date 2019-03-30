package com.telekom.controller;


import com.telekom.entity.PhoneNumber;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.OptionGroupDTO;
import com.telekom.entityDTO.Page;
import com.telekom.entityDTO.TariffDTO;
import com.telekom.service.OptionGroupService;
import com.telekom.service.OptionService;
import com.telekom.service.PhoneNumberService;
import com.telekom.service.TariffService;
import javafx.scene.control.Pagination;
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

    @GetMapping(value = "/test")
    public List<OptionGroupDTO> findPaginated(Model model, @RequestParam Integer page) {

        Page<OptionGroupDTO> resultPage = optionGroupService.getPage(GROUPS_PER_PAGE, page);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage.getData();
    }

     @GetMapping(value = "/phoneNumbers")
    public List<PhoneNumber> pagePhoneNumber(Model model, @RequestParam Integer page) {

        Page<PhoneNumber> resultPage = phoneNumberService.getPage(GROUPS_PER_PAGE, 1);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage.getData();
    }

    @GetMapping(value = "/options/optionPages")
    public Page<OptionDTO> pageOption(Model model, @RequestParam Integer page, @RequestParam (required=false)Integer id) {
        Page<OptionDTO> resultPage;
        if (id!=null)resultPage = optionService.getPage(GROUPS_PER_PAGE, page, id);
        else resultPage = optionService.getPage(GROUPS_PER_PAGE, page);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage;
    }

    @GetMapping(value = "/tariffs/tariffPages")
    public Page<TariffDTO> pageTariff(Model model, @RequestParam Integer page, @RequestParam (required=false)Integer id) {
        Page<TariffDTO> resultPage;
        if (id!=null)resultPage = tariffService.getPage(GROUPS_PER_PAGE, page, id);
        else resultPage = tariffService.getPage(GROUPS_PER_PAGE, page);
        if (page > resultPage.getTotalPages()) {
            throw new NullPointerException();
        }
        return resultPage;
    }



    @GetMapping(value = "/search")
    public List<OptionGroupDTO> findByName(Model model, @RequestParam(value = "name", required = false) String name) {

        List<OptionGroupDTO> resultPage = optionGroupService.getByName(name);
        model.addAttribute("optionGroups", resultPage);
        return resultPage;
    }


}

