package com.telekom.controller;

import com.telekom.entity.Tariff;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;


public class ProductsController {

    private ArrayList<Tariff> tariffs= new ArrayList<Tariff>();

    @GetMapping("/tariffs/new")
    public String newTarif(){
        return "newTariff";
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        model.addAttribute("tariffs", tariffs);
        return "tariffs";
    }


    @PostMapping("/tariffs/new")
    public String signUp(@ModelAttribute Tariff tariff){
        tariffs.add(tariff);
        return "redirect:/users";
    }

}
