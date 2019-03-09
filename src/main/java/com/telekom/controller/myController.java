package com.telekom.controller;

import com.telekom.dao.ClientDAO;
import com.telekom.dao.JPAClientDao;
import com.telekom.dao.TestDAO;
import com.telekom.entity.Address;
import com.telekom.entity.Client;
import com.telekom.entity.Tariff;
import com.telekom.service.ClientService;
import com.telekom.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;



@Controller
public class myController {

    @Autowired
    //@Qualifier("JPAClientDao")
    private ClientService clientService;


    private ArrayList<Client> clients= new ArrayList<Client>();
    @GetMapping("/view")
    public String view(@RequestParam(value = "name", required = false, defaultValue="stranger ") String name, Model model) {
        model.addAttribute("msg","Hello "+name);
        return "index";
    }


    @RequestMapping(value="/view",params="Customer",method=RequestMethod.POST)
    public String action1()
    {
        return "index";
    }
    @RequestMapping(value="/view",params="Shop",method=RequestMethod.POST)
    public String action2()
    {
        return "redirect:/shopagent";
    }

    @GetMapping("/shopagent")
    public String agent()
     {
         return "shopagent";
    }

    @GetMapping("/users")
    public String getUsers(Model model) throws SQLException {
    model.addAttribute("clients", clientService.getAll());
     return "users";
        }


    @GetMapping( "/users/new")
    public ModelAndView getRegistrationForm() {
        Client customer = new Client();
        Address ba = new Address();
        customer.setAddress(ba);
        return new ModelAndView("sign", "client", customer);
    }

    // to insert the data
    @RequestMapping(value = "/users/new", method = RequestMethod.POST)
    public String registerCustomer(@Valid @ModelAttribute(value = "customer") Client customer, Model model,
                                   BindingResult result) {
        if (result.hasErrors())
            return "index";
        clientService.add(customer);
       // model.addAttribute("registrationSuccess", "Registered Successfully. Login using username and password");
        return "redirect:/users";
    }
/*
    @PostMapping("/users/new")
    public String signUp(@ModelAttribute Client client){
       clientService.add(client);
       return "redirect:/users";
    }
*/

    @Autowired
    private TariffService tariffService;

    @GetMapping("/tariffs")
    public String getTariffs(Model model) throws SQLException {
        model.addAttribute("tariffs", tariffService.getAll());
        return "tariffs";
    }

    @GetMapping("/tariffs/new")
    public String newTariff(){
        return "newTariff";
    }

    @PostMapping("/tariffs/new")
    public String signUp(@ModelAttribute Tariff tariff){
        tariffService.add(tariff);
        return "redirect:/tariffs";
    }

}

