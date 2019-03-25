package com.telekom.controller;


import com.telekom.entityDTO.OptionGroupDTO;
import com.telekom.service.OptionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OptionRestController {
    private static final int GROUPS_PER_PAGE = 5;
    @Autowired
    OptionGroupService optionGroupService;

    @GetMapping(value = "/test")
    public List<OptionGroupDTO> findPaginated(Model model, @RequestParam(value = "page", required = false) Integer page) {

        List<OptionGroupDTO> resultPage = optionGroupService.getPage(GROUPS_PER_PAGE, page);
        model.addAttribute("optionGroups", resultPage);
        if (page > optionGroupService.getTotalPages(GROUPS_PER_PAGE)) {
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

