package com.telekom.controller;

import com.telekom.service.OptionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminMenuController {

    @Autowired
    private OptionGroupService optionGroupService;

    @GetMapping("/tariffs")
    public String getTariffs(Model model)  {
        model.addAttribute("table", "edit");
        return "tariffs";
    }
    @GetMapping("/optionGroups")
    public String getOptionGroups(Model model){
        model.addAttribute("optionGroups", optionGroupService.getAll());
        return "optionGroups";
    }
    @GetMapping("/options")
    public String getOptions(Model model)  {
        model.addAttribute("table", "edit");
        return "options";
    }
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("table", "edit");
        return "clients";
    }

}



