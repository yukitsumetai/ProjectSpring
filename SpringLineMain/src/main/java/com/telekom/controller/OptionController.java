package com.telekom.controller;

import com.telekom.model.dto.OptionDto;
import com.telekom.service.api.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SessionAttributes(types = OptionDto.class)
@RequestMapping(value = "/options")
@Controller
public class OptionController {

    @Autowired
    private OptionService optionService;


    @GetMapping("/new")
    public String newOption(Model model) {
        OptionDto option = new OptionDto();
        model.addAttribute( option);
        return "addOption";
    }

    private String setTariffOrOption(Model model, boolean tariff, OptionDto option) {
        if (tariff) {
            model.addAttribute("table", "option");
            return "tariffs";
        }
        optionService.add(option);
        return "redirect:/options";
    }

    @PostMapping("/new")
    public String newOptionAdd(Model model, @Valid OptionDto option, BindingResult bindingResult, @RequestParam(name = "isValid", required = false) boolean validity,
                               @RequestParam(name = "relation") String relation, @RequestParam(name = "group", required = false) Integer groupId,
                               @RequestParam(name = "tariffs", required = false) boolean tariff) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Tariff was not added because received data contains some errors");
            return "addOption";
        } else {
            if (!validity) option.setIsValid(false);
            optionService.setOptionGroup(option, groupId);
            if (!relation.equals("alone")) {
                boolean flag=false;
                if (relation.equals("parent")) flag=true;
                model.addAttribute("parent", flag);
                model.addAttribute("tariff", tariff);
                return "optionsRelations";
            }
            return setTariffOrOption(model, tariff, option);
        }
    }

    @PostMapping("/new/parent")
    public String newOptionParent(Model model, OptionDto option, @RequestParam(name = "optionID2", required = false) Integer id, @RequestParam(name = "action") boolean tariff) {
        optionService.setParent(option, id);
        return setTariffOrOption(model, tariff, option);
    }

    @PostMapping("/new/children")
    public String newOptionChildren(Model model, OptionDto option, @RequestParam(name = "optionID2", required = false) List<Integer> id,
                                    @RequestParam(name = "action") boolean tariff) {
        optionService.setChildren(option, id);
        return setTariffOrOption(model, tariff, option);
    }

    @PostMapping("/new/tariffs")
    public String newOptionTariffs(OptionDto option, @RequestParam(name = "tariffID2", required = false) List<Integer> id) {
        optionService.setCompatibleTariffs(option, id);
        optionService.add(option);
        return "redirect:/options";
    }

    @GetMapping("/edit/{id}")
    public String getEditOption(Model model, @PathVariable(value = "id") Integer id) {
        OptionDto option = optionService.getOne(id);
        model.addAttribute("option", option);
        return "editOption";
    }

    private void tariffPage(Model model, OptionDto option) {
        model.addAttribute("table", "optionEdit");
        model.addAttribute("id", option.getId());
        model.addAttribute("existing", option.getCompatibleTariffs());
    }

    private String editTariffOrOption(Boolean tariff, Model model, OptionDto option){
        if (tariff) {
            tariffPage(model, option);
            return "tariffs";
        }
        optionService.editOption(option);
        return "redirect:/options";
    }

    @PostMapping("/edit")
    public String editOption(Model model, @ModelAttribute(value = "option") OptionDto option, @RequestParam(name = "isValid", required = false) boolean validity,
                             @RequestParam(name = "relation") String relation, @RequestParam(name = "group", required = false) Integer groupId,
                             @RequestParam(name = "tariffs", required = false) boolean tariff) {
        optionService.setOptionGroup(option, groupId);
        if (!relation.equals("nothing")) {
            if (relation.equals("alone")) {
                optionService.removeRelations(option);
            } else {
                if (relation.equals("parent")) {
                    Set<OptionDto> parent = new HashSet<>();
                    parent.add(option.getParent());
                    model.addAttribute("existing", parent);
                    model.addAttribute("parent", true);
                } else {
                    model.addAttribute("parent", false);
                    model.addAttribute("existing", option.getChildren());
                }
                model.addAttribute("optionId", option.getId());
                optionService.removeRelations(option);
                model.addAttribute("tariff", tariff);
                return "optionsRelations";
            }
        }
       return editTariffOrOption(tariff, model, option);
    }

    @PostMapping("/edit/parent")
    public String existingOptionParent(Model model, @ModelAttribute(value = "option") OptionDto option, @RequestParam(name = "optionID2", required = false) Integer id, @RequestParam(name = "action") boolean tariff) {
        optionService.setParent(option, id);
        return editTariffOrOption(tariff, model, option);
    }


    @PostMapping("/edit/children")
    public String existingOptionChildren(Model model, @ModelAttribute(value = "option") OptionDto option, @RequestParam(name = "optionID2", required = false) List<Integer> id,
                                         @RequestParam(name = "action") boolean tariff) {
        optionService.setChildren(option, id);
        return editTariffOrOption(tariff, model, option);
    }

    @PostMapping("/edit/tariffs")
    public String existingOptionTariffs(@ModelAttribute(value = "option") OptionDto option, @RequestParam(name = "tariffID2", required = false) List<Integer> id) {
        optionService.setCompatibleTariffs(option, id);
        optionService.editOption(option);
        return "redirect:/options";
    }


    @GetMapping("/delete/{id}")
    public String deleteOption(@PathVariable(value = "id") Integer id) {
        optionService.deleteOption(id);
        return "redirect:/options";
    }
}
