package com.telekom.controller;

import com.telekom.entity.*;
import com.telekom.entityDTO.AddressDTO;
import com.telekom.entityDTO.ClientDTO;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.TariffDTO;
import com.telekom.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
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

    @PostMapping("/existingContract/contract")
    public String showContract(Model model, @RequestParam(name = "phoneNumber") String number) {
        model.addAttribute( "contractDTO", contractService.getOne(number));
        return "confirmation";
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

    @GetMapping("/users")
    public String getUsers(Model model) throws SQLException {
        model.addAttribute("clients", clientService.getAll());
        return "clients";
    }


    @PostMapping("/getUser")
    public String registerCustomer(Model model, @RequestParam(name = "phoneNumber") String number) {
      List<ClientDTO> clients=new ArrayList<>();
       clients.add(clientService.getOne(number));
       model.addAttribute( "clients", clients);
        return "confirmation";
    }

    @Autowired
    private TariffService tariffService;

    @Autowired
    private OptionService optionService;

    @GetMapping("/tariffs")
    public String getTariffs(Model model) throws SQLException {
        model.addAttribute("tariffs", tariffService.getAll());
        String table = "edit";
        model.addAttribute("table", table);
        return "tariffs";
    }

    @GetMapping("/tariffs/new")
    public String newTariff(Model model) throws SQLException {
        model.addAttribute("options", optionService.getAll());

        return "newTariff";
    }

    @PostMapping("/tariffs/new")
    public String newTariffAdd(@ModelAttribute Tariff tariff, @RequestParam(name = "opt", required = false) List<Integer> opts) {
        if (opts == null) {
            tariffService.add(tariff);
        } else tariffService.add(tariff, opts);
        return "redirect:/tariffs";
    }


    @GetMapping("/tariffs/edit/{id}")
    public ModelAndView getEditForm(@PathVariable(value = "id") Integer id) {
        TariffDTO tariff = tariffService.getOne(id);
        return new ModelAndView("editTariff", "tariff", tariff);
    }

    @PostMapping("/tariffs/edit")
    public String editProduct(@ModelAttribute(value = "tariff") TariffDTO tariffDto) {
        tariffService.editTariff(tariffDto);
        return "redirect:/tariffs";
    }

    @GetMapping("/tariffs/delete/{id}")
    public String deleteTariff(@PathVariable(value = "id") Integer id) {
        tariffService.deleteTariff(id);
        return "redirect:/tariffs";
    }

    @GetMapping("/options")
    public String getOptions(Model model) throws SQLException {
        model.addAttribute("options", optionService.getAll());
        String table = "edit";
        model.addAttribute("table", table);
        return "options";
    }

    @GetMapping("/options/new")
    public String newOption(Model model) throws SQLException {
        model.addAttribute("tariffs", tariffService.getAll());
        return "newOption";
    }

    @PostMapping("/options/new")
    public String newOptionAdd(@ModelAttribute Option option, @RequestParam(name = "opt", required = false) List<Integer> opts) {
        if (opts == null) {
            optionService.add(option);
        } else optionService.add(option, opts);
        return "redirect:/options";
    }

    @GetMapping("/options/edit/{id}")
    public ModelAndView getEditOption(@PathVariable(value = "id") Integer id) {
        OptionDTO option = optionService.getOne(id);
        return new ModelAndView("editOption", "option", option);
    }

    @PostMapping("/options/edit")
    public String editProduct(@ModelAttribute(value = "option") Option option) {
        optionService.editOption(option);
        return "redirect:/options";
    }

    @GetMapping("/options/delete/{id}")
    public String deleteOption(@PathVariable(value = "id") Integer id) {
        optionService.deleteOption(id);
        return "redirect:/options";
    }


}



