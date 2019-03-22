package com.telekom.controller;

import com.telekom.entity.Tariff;
import com.telekom.entityDTO.TariffDTO;
import com.telekom.service.OptionService;
import com.telekom.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

@SessionAttributes(types = TariffDTO.class)
@RequestMapping(value = "/tariffs")
@Controller
public class TariffController {
    @Autowired
    private TariffService tariffService;

    @Autowired
    private OptionService optionService;


    @GetMapping("/new")
    public String newTariff(Model model) throws SQLException {
        TariffDTO tariff=new TariffDTO();
        model.addAttribute("tariff", tariff);
        model.addAttribute("options", optionService.getAll());
        return "newTariff";
    }

    @PostMapping("/new")
    public String newTariffAdd( TariffDTO tariff, @RequestParam(name = "opt", required = false) List<Integer> opts) {
        tariffService.add(tariff, opts);
        return "redirect:/tariffs";
    }


    @GetMapping("/edit/{id}")
    public ModelAndView getEditForm(@PathVariable(value = "id") Integer id) {
        TariffDTO tariff = tariffService.getOne(id);
        return new ModelAndView("editTariff", "tariff", tariff);
    }


    @PostMapping("/edit")
    public String editProduct(@ModelAttribute(value = "tariff") TariffDTO tariffDto, @RequestParam(name = "isValid", required=false) boolean validity) {
        if (!validity) tariffDto.setIsValid(false);
        tariffService.editTariff(tariffDto);
        return "redirect:/tariffs";
    }

    @GetMapping("/delete/{id}")
    public String deleteTariff(Model model, @PathVariable(value = "id") Integer id) {
        tariffService.deleteTariff(id);
        return "redirect:/tariffs";
    }

}
