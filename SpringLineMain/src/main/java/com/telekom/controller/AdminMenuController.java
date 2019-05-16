package com.telekom.controller;

import com.telekom.service.api.OptionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminMenuController {
    String table = "table";
    @Autowired
    private OptionGroupService optionGroupService;

    @Autowired
    private Logger logger;

    /**
     * Returns table of tariffs
     * @param model edit parameter means that tariff cannot be choosen, but edited
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/tariffs")
    public String getTariffs(Model model) {
        model.addAttribute(table, "edit");
        logger.info("/tariffs");
        return "tariffs";
    }

    /**
     * Returns table of  option groups
     * @param model edit parameter means that option group cannot be choosen, but edited
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/optionGroups")
    public String getOptionGroups(Model model) {
        model.addAttribute("optionGroups", optionGroupService.getAll());
        logger.info("/optionGroups");
        return "optionGroups";
    }

    /**
     * Returns table of options
     * @param model edit parameter means that option cannot be choosen, but edited
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/options")
    public String getOptions(Model model) {
        model.addAttribute(table, "edit");
        logger.info("/options");
        return "options";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute(table, "edit");
        logger.info("/users");
        return "clients";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/client")
    public String Test(Model model) {
        return "newClient";
    }
}



