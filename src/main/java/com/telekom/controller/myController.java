package com.telekom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class myController {

    @GetMapping("/view")
    public String view(@RequestParam(value = "name", required = false, defaultValue="stranger ") String name, Model model) {
        model.addAttribute("msg","Hello"+name);
        return "index";
    }
}
