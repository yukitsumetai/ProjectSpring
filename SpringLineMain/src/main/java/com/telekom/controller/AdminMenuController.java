package com.telekom.controller;

import com.telekom.service.api.OptionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

@Controller
public class AdminMenuController {
    String table = "table";
    @Autowired
    private OptionGroupService optionGroupService;

    @Autowired
    private Logger logger;

    @GetMapping("/tariffs")
    public String getTariffs(Model model) {
        model.addAttribute(table, "edit");
        logger.info("/tariffs");
        return "tariffs";
    }

    @GetMapping("/optionGroups")
    public String getOptionGroups(Model model) {
        model.addAttribute("optionGroups", optionGroupService.getAll());
        logger.info("/optionGroups");
        return "optionGroups";
    }

    @GetMapping("/options")
    public String getOptions(Model model) {
        model.addAttribute(table, "edit");
        logger.info("/options");
        return "options";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute(table, "edit");
        logger.info("/users");
        return "clients";
    }

    @GetMapping("/client")
    public String Test(Model model) {
        return "newClient";
    }
}



