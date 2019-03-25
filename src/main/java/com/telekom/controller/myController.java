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
    private ContractService contractService;

    @GetMapping("/view")
    public String view(@RequestParam(value = "name", required = false, defaultValue = "stranger ") String name, Model model) {
        model.addAttribute("msg", "Hello " + name);
        return "index";
    }

    @GetMapping("/existingContract/search")
    public String searchContract() {

        return "search";
    }

    @GetMapping("/combo")
    public String cb() {

        return "elements/ComboBox element";
    }

    @RequestMapping(value = "/view", params = "Customer", method = RequestMethod.POST)
    public String action1() {
        return "index";
    }

    @RequestMapping(value = "/view", params = "Shop", method = RequestMethod.POST)
    public String action2() {
        return "redirect:/shopagent";
    }

    @GetMapping("/shopagent")
    public String agent() {
        return "shopagent";
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

    @Autowired
    private TariffService tariffService;

    @Autowired
    private OptionService optionService;
    @Autowired
    private OptionGroupService optionGroupService;

    @GetMapping("/tariffs")
    public String getTariffs(Model model) throws SQLException {
        model.addAttribute("tariffs", tariffService.getAll());
        model.addAttribute("table", "edit");
        return "tariffs";
    }
    @GetMapping("/optionGroups")
    public String getOptionGroups(Model model) throws SQLException {
        model.addAttribute("optionGroups", optionGroupService.getAll());
        return "optionGroups";
    }
    @GetMapping("/options")
    public String getOptions(Model model) throws SQLException {
        model.addAttribute("options", optionService.getAll());
        model.addAttribute("table", "edit");
        return "options";
    }
    @GetMapping("/users")
    public String getUsers(Model model) throws SQLException {
        model.addAttribute("clients", clientService.getAll());
        model.addAttribute("table", "edit");
        return "clients";
    }



}



