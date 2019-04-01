package com.telekom.controller;

import com.telekom.entityDTO.ClientDTO;
import com.telekom.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


@Controller
public class myController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private TariffService tariffService;

    @Autowired
    private OptionGroupService optionGroupService;




    @GetMapping("/tariffs")
    public String getTariffs(Model model)  {
        model.addAttribute("tariffs", tariffService.getAll());
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
        model.addAttribute("clients", clientService.getAll());
        model.addAttribute("table", "edit");
        return "clients";
    }

    @PostMapping("/getUser")
    public String registerCustomer(Model model, @RequestParam(name = "phoneNumber") String number) {
        ClientDTO client = clientService.getOne(number);
        if (client == null) {
            return "clients";
        }

        List<ClientDTO> clients = new ArrayList<>();
        clients.add(client);
        model.addAttribute("clients", clients);
        return "clients";
    }


}



