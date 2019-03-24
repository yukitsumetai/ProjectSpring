package com.telekom.controller;

import com.telekom.entityDTO.TariffDTO;
import com.telekom.service.OptionService;
import com.telekom.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        TariffDTO tariff = new TariffDTO();
        model.addAttribute("tariff", tariff);
        return "addTariff";
    }

    @PostMapping("/new")
    public String newTariffAdd(Model model, TariffDTO tariff, @RequestParam(name = "isValid", required = false) boolean validity,
                               @RequestParam(name = "compatibleOptions", required = false) boolean options) {
        if (!validity) tariff.setIsValid(false);
        if (options) {
            model.addAttribute("table", "tariffAdd");
            model.addAttribute("options", optionService.getAllValid());
            return "options";
        }
        tariffService.add(tariff);
        return "redirect:/tariffs";
    }

    @PostMapping("/new/options")
    public String newTariffOptions(TariffDTO tariff, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        tariffService.SetOptions(tariff, id);
        tariffService.add(tariff);
        return "redirect:/tariffs";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(Model model, @PathVariable(value = "id") Integer id) {
        TariffDTO tariff = tariffService.getOne(id);
        model.addAttribute("tariff", tariff);
        return "editTariff";
    }


    @PostMapping("/edit")
    public String editProduct(Model model, @ModelAttribute(value = "tariff") TariffDTO tariff, @RequestParam(name = "compatibleOptions", required = false) boolean options) {
        if (options) {
            model.addAttribute("table", "tariffEdit");
            model.addAttribute("existingOptions", tariff.getOptions());
            model.addAttribute("options", optionService.getAllValid());
            return "options";
        }
        tariffService.editTariff(tariff);
        return "redirect:/tariffs";
    }

    @PostMapping("/edit/options")
    public String editTariffOptions(@ModelAttribute(value = "tariff") TariffDTO tariff, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        tariffService.SetOptions(tariff, id);
        tariffService.editTariff(tariff);
        return "redirect:/tariffs";
    }

    @GetMapping("/delete/{id}")
    public String deleteTariff(@PathVariable(value = "id") Integer id) {
        tariffService.deleteTariff(id);
        return "redirect:/tariffs";
    }

}
