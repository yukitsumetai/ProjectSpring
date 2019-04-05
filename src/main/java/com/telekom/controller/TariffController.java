package com.telekom.controller;

import com.telekom.entityDTO.TariffDTO;
import com.telekom.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SessionAttributes(types = TariffDTO.class)
@RequestMapping(value = "/tariffs")
@Controller
public class TariffController {
    @Autowired
    private TariffService tariffService;

    @GetMapping("/new")
    public String newTariff(Model model) {
        TariffDTO tariff = new TariffDTO();
        model.addAttribute( tariff);
        return "addTariff";
    }

    @PostMapping("/new")
    public String newTariffAdd(Model model, @Valid TariffDTO tariff, BindingResult bindingResult, @RequestParam(name = "isValid", required = false) boolean validity,
                               @RequestParam(name = "compatibleOptions", required = false) boolean options) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Tariff cannot be added because received data contains some errors");
            return "addTariff";
        } else {
            if (!validity) tariff.setIsValid(false);
            if (options) {
                return "options";
            }
            tariffService.add(tariff);
            return "redirect:/tariffs";
        }
    }

    @PostMapping("/new/options")
    public String newTariffOptions(TariffDTO tariff, @RequestParam(name = "optionID2", required = false) List<Integer> id) {
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
            model.addAttribute("existing", tariff.getOptions());
            model.addAttribute("id", tariff.getId());
            return "options";
        }
        tariffService.editTariff(tariff);
        return "redirect:/tariffs";
    }

    @PostMapping("/edit/options")
    public String editTariffOptions(@ModelAttribute(value = "tariff") TariffDTO tariff, @RequestParam(name = "optionID2", required = false) List<Integer> id) {
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
