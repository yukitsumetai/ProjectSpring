package com.telekom.controller;

import com.telekom.model.dto.TariffDto;
import com.telekom.service.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@SessionAttributes(types = TariffDto.class)
@RequestMapping(value = "/tariffs")
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class TariffController {
    private final static String TARIFF_PAGE = "redirect:/tariffs";
    @Autowired
    private TariffService tariffService;

    @GetMapping("/new")
    public String newTariff(Model model) {
        TariffDto tariff = new TariffDto();
        model.addAttribute(tariff);
        return "addTariff";
    }

    @PostMapping("/new")
    public String newTariffAdd(Model model, @Valid TariffDto tariff, BindingResult bindingResult, @RequestParam(name = "isValid", required = false) boolean validity,
                               @RequestParam(name = "isPromoted", required = false) boolean promotion,
                               @RequestParam(name = "compatibleOptions", required = false) boolean options, SessionStatus status) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Tariff cannot be added because received data contains some errors");
            return "addTariff";
        } else {
            if (!validity) tariff.setIsValid(false);
            tariff.setPromoted(promotion);
            if (options) {
                return "options";
            }
            tariffService.add(tariff);
            tariffService.notify(tariff);
            status.setComplete();
            return TARIFF_PAGE;
        }
    }

    @PostMapping("/new/options")
    public String newTariffOptions(TariffDto tariff, @RequestParam(name = "optionID2", required = false) List<Integer> id, SessionStatus status) {
        tariffService.setOptionsDto(tariff, id);
        tariffService.add(tariff);
        tariffService.notify(tariff);
        status.setComplete();
        return TARIFF_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(Model model, @PathVariable(value = "id") Integer id) {
        TariffDto tariff = tariffService.getTariff(id);
        model.addAttribute("tariff", tariff);
        return "editTariff";
    }


    @PostMapping("/edit")
    public String editProduct(Model model, @ModelAttribute(value = "tariff") TariffDto tariff, @RequestParam(name = "compatibleOptions", required = false) boolean options,
                              @RequestParam(name = "isPromoted", required = false) boolean promotion,
                              @RequestParam(name = "initialState") boolean initialState, SessionStatus status) {
        tariff.setPromoted(promotion);
        if (options) {
            model.addAttribute("existing", tariff.getOptions());
            model.addAttribute("id", tariff.getId());
            model.addAttribute("promoted", initialState);
            return "options";
        }
        tariffService.editTariff(tariff);
        tariffService.notify(tariff, initialState);
        status.setComplete();
        return TARIFF_PAGE;
    }

    @PostMapping("/edit/options")
    public String editTariffOptions(@ModelAttribute(value = "tariff") TariffDto tariff, @RequestParam(name = "optionID2", required = false) List<Integer> id,
                                    @RequestParam(name = "initialState") boolean initialState, SessionStatus status) {
        tariffService.setOptionsDto(tariff, id);
        tariffService.editTariff(tariff);
        tariffService.notify(tariff, initialState);
        status.setComplete();
        return TARIFF_PAGE;
    }

    /**
     * gfujcgjgv
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteTariff(@PathVariable(value = "id") Integer id) {
        tariffService.deleteTariff(id);
        tariffService.notifyDeleted();
        return TARIFF_PAGE;
    }


}
