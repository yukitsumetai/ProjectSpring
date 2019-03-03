package com.telekom.controller;

import com.telekom.entity.Client;
import com.telekom.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
public class myController {
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
    public String view()
     {
         return "shopagent";
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        Client c1=new Client("dgfgd", "grgr", "regre", "33.44.33", "rgrege");
        clients.add(c1);
    model.addAttribute("clients", clients);
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

