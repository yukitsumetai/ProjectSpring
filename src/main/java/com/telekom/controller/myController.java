package com.telekom.controller;

import com.telekom.entity.*;
import com.telekom.service.ClientService;
import com.telekom.service.OptionService;
import com.telekom.service.PhoneNumberService;
import com.telekom.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;


@Controller
public class myController {

    @Autowired
    //@Qualifier("JPAClientDao")
    private ClientService clientService;

    @GetMapping("/view")
    public String view(@RequestParam(value = "name", required = false, defaultValue = "stranger ") String name, Model model) {
        model.addAttribute("msg", "Hello " + name);
        return "index";
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
        return "users";
    }

    @Autowired
    private PhoneNumberService phoneNumberService;

    @RequestMapping(value = "/users/new")
    public ModelAndView getRegistrationForm(Model model) {
        Client client = new Client();
        Address ba = new Address();
        client.setAddress(ba);
        model.addAttribute("numbers", phoneNumberService.getNumbers());
        //model.addAttribute("client", client);
        //User user = new User();
        //client.setUser(user);
        return new ModelAndView("sign", "client", client);
    }

    // to insert the data
    @PostMapping("/users/new")
    public String registerCustomer(@Valid @ModelAttribute("client") Client client, BindingResult result) {
        if (result.hasErrors())
            return "index";
        clientService.add(client);
        // model.addAttribute("registrationSuccess", "Registered Successfully. Login using username and password");
        return "redirect:/users";
    }

    @Autowired
    private TariffService tariffService;

    @Autowired
    private OptionService optionService;

    @GetMapping("/tariffs")
    public String getTariffs(Model model) throws SQLException {
        model.addAttribute("tariffs", tariffService.getAll());
        return "tariffs";
    }

    @GetMapping("/tariffs/new")
    public String newTariff(Model model) throws SQLException {
        model.addAttribute("options", optionService.getAll());

        return "newTariff";
    }

    @PostMapping("/tariffs/new")
    public String newTariffAdd(@ModelAttribute Tariff tariff, @RequestParam("opt") List<Integer> opts) {
        tariffService.add(tariff, opts);
        return "redirect:/tariffs";
    }


    @GetMapping("/options")
    public String getOptions(Model model) throws SQLException {
        model.addAttribute("options", optionService.getAll());
        return "options";
    }

    @GetMapping("/options/new")
    public String newOption(Model model) throws SQLException {
        model.addAttribute("tariffs", tariffService.getAll());
        return "newOption";
    }

    @PostMapping("/options/new")
    public String newOptionAdd(@ModelAttribute Option option, @RequestParam("opt") List<Integer> opts) {
        optionService.add(option, opts);
        return "redirect:/options";
    }

}

