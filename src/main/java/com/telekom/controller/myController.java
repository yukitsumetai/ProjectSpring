package com.telekom.controller;

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
    private ArrayList<User> users= new ArrayList<User>();
    @GetMapping("/view")
    public String view(@RequestParam(value = "name", required = false, defaultValue="stranger ") String name, Model model) {
        model.addAttribute("msg","Hello "+name);
        return "index";
    }

    @GetMapping("/raw")
    @ResponseBody
    public String raw() {
        return "Raw data";
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        User u1=new User("John", "sd@t.com");
        User u2=new User("Tom", "fe@tr.com");

     users.add(u1);
     users.add(u2);

    model.addAttribute("users", users);
     return "users";
        }

    @GetMapping("/users/new")
    public String getSignUp(){
        return "/sign";
    }

    @PostMapping("/users/new")
    public String signUp(@ModelAttribute User user){
       users.add(user);
       return "redirect:/users";
    }
}

