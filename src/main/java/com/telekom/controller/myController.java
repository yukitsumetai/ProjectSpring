package com.telekom.controller;

import com.telekom.dao.ClientDAO;
import com.telekom.dao.JPAClientDao;
import com.telekom.dao.TestDAO;
import com.telekom.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;



@Controller
public class myController {

    @Autowired
    private JPAClientDao ClientDao;


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

    model.addAttribute("clients", ClientDao.getAll());
     return "users";
        }

    @GetMapping("/users/new")
    public String getSignUp(){
        return "/sign";
    }



    @PostMapping("/users/new")
    public String signUp(@ModelAttribute Client client){
      clients.add(client);
       return "redirect:/users";
    }
}

