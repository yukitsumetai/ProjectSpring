package com.telekom.controller;

import com.telekom.entity.Option;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.service.OptionService;
import com.telekom.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

@SessionAttributes(types = OptionDTO.class)
@RequestMapping(value = "/options")
@Controller
public class OptionController {
    @Autowired
    private TariffService tariffService;

    @Autowired
    private OptionService optionService;

    @GetMapping("/new")
    public String newOption(Model model) throws SQLException {
       OptionDTO option=new OptionDTO();
        model.addAttribute("option", option);
        model.addAttribute("table", "add");
        model.addAttribute("tariffs", tariffService.getAll());
        return "newOption";
    }

    @PostMapping("/new")
    public String newOptionAdd(OptionDTO option, @RequestParam(name = "opt", required = false) List<Integer> tariffs,@RequestParam(name = "isValid", required = false) boolean validity) {
        if(!validity)option.setIsValid(false);
        optionService.add(option, tariffs);
        return "redirect:/options";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditOption(@PathVariable(value = "id") Integer id) {
        OptionDTO option = optionService.getOne(id);
        return new ModelAndView("editOption", "option", option);
    }

    @PostMapping("/edit")
    public String editProduct(@ModelAttribute(value = "option") OptionDTO option,@RequestParam(name = "isValid", required = false) boolean validity) {
        if(!validity)option.setIsValid(false);
        optionService.editOption(option);
        return "redirect:/options";
    }

    @GetMapping("/delete/{id}")
    public String deleteOption(@PathVariable(value = "id") Integer id) {
        optionService.deleteOption(id);
        return "redirect:/options";
    }
}
