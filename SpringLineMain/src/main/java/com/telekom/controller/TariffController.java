package com.telekom.controller;

import com.telekom.model.dto.TariffDto;
import com.telekom.service.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SessionAttributes(types = TariffDto.class)
@RequestMapping(value = "/tariffs")
@Controller
public class TariffController {
    public final static String tariffPage="redirect:/tariffs";
    @Autowired
    private TariffService tariffService;

    @GetMapping("/new")
    public String newTariff(Model model) {
        TariffDto tariff = new TariffDto();
        model.addAttribute( tariff);
        return "addTariff";
    }

    @PostMapping("/new")
    public String newTariffAdd(Model model, @Valid TariffDto tariff, BindingResult bindingResult, @RequestParam(name = "isValid", required = false) boolean validity,
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
            return tariffPage;
        }
    }

    @PostMapping("/new/options")
    public String newTariffOptions(TariffDto tariff, @RequestParam(name = "optionID2", required = false) List<Integer> id) {
        tariffService.setOptions(tariff, id);
        tariffService.add(tariff);
        return tariffPage;
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(Model model, @PathVariable(value = "id") Integer id) {
        TariffDto tariff = tariffService.getOne(id);
        model.addAttribute("tariff", tariff);
        return "editTariff";
    }


    @PostMapping("/edit")
    public String editProduct(Model model, @ModelAttribute(value = "tariff") TariffDto tariff, @RequestParam(name = "compatibleOptions", required = false) boolean options) {
        if (options) {
            model.addAttribute("existing", tariff.getOptions());
            model.addAttribute("id", tariff.getId());
            return "options";
        }
        tariffService.editTariff(tariff);
        return tariffPage;
    }

    @PostMapping("/edit/options")
    public String editTariffOptions(@ModelAttribute(value = "tariff") TariffDto tariff, @RequestParam(name = "optionID2", required = false) List<Integer> id) {
        tariffService.setOptions(tariff, id);
        tariffService.editTariff(tariff);
        return tariffPage;
    }

    @GetMapping("/delete/{id}")
    public String deleteTariff(@PathVariable(value = "id") Integer id) {
        tariffService.deleteTariff(id);
        return tariffPage;
    }

}
