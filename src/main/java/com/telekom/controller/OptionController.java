package com.telekom.controller;

import com.telekom.entityDTO.OptionDTO;
import com.telekom.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SessionAttributes(types = OptionDTO.class)
@RequestMapping(value = "/options")
@Controller
public class OptionController {

    @Autowired
    private OptionService optionService;


    @GetMapping("/new")
    public String newOption(Model model) throws SQLException {
        OptionDTO option = new OptionDTO();
        model.addAttribute("optionDTO", option);
        model.addAttribute("table", "add");
        return "addOption";
    }

    @PostMapping("/new")
    public String newOptionAdd(Model model, @Valid OptionDTO option, BindingResult bindingResult, @RequestParam(name = "isValid", required = false) boolean validity,
                               @RequestParam(name = "relation") String relation, @RequestParam(name = "group", required = false) Integer groupId,
                               @RequestParam(name = "tariffs", required = false) boolean tariff) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Tariff was not added because received data contains some errors");
            return "addOption";
        }
        else{
        if (!validity) option.setIsValid(false);
        optionService.SetOptionGroup(option, groupId);
        if (!relation.equals("alone")) {
            if (relation.equals("parent")) model.addAttribute("parent", true);
            else model.addAttribute("parent", false);
            model.addAttribute("tariff", tariff);
            return "optionsRelations";
        }
        if (tariff) {
            model.addAttribute("table", "option");
            return "tariffs";
        }
        optionService.add(option);
        return "redirect:/options";}
    }

    @PostMapping("/new/parent")
    public String newOptionParent(Model model, OptionDTO option, @RequestParam(name = "optionID2", required = false) Integer id, @RequestParam(name = "action") boolean tariff) {
        optionService.SetParent(option, id);
        if (tariff) {
            model.addAttribute("table", "option");
            return "tariffs";
        }
        optionService.add(option);
        return "redirect:/options";
    }

    @PostMapping("/new/children")
    public String newOptionChildren(Model model, OptionDTO option, @RequestParam(name = "optionID2", required = false) List<Integer> id,
                                    @RequestParam(name = "action") boolean tariff) {
       optionService.SetChildren(option, id);
        if (tariff) {
            model.addAttribute("table", "option");
            return "tariffs";
        }
        optionService.add(option);
        return "redirect:/options";
    }

    @PostMapping("/new/tariffs")
    public String newOptionTariffs(OptionDTO option, @RequestParam(name = "tariffID2", required = false) List<Integer> id) {
        optionService.SetCompatibleTariffs(option, id);
        optionService.add(option);
        return "redirect:/options";
    }


    @GetMapping("/edit/{id}")
    public String getEditOption(Model model,@PathVariable(value = "id") Integer id) {
        OptionDTO option = optionService.getOne(id);
        model.addAttribute("option", option);
        return "editOption";
    }

    private void tariffPage(Model model, OptionDTO option) {
        model.addAttribute("table", "optionEdit");
        model.addAttribute("id", option.getId());
        model.addAttribute("existing", option.getCompatibleTariffs());
    }

    @PostMapping("/edit")
    public String editOption(Model model, @ModelAttribute(value = "option") OptionDTO option,  @RequestParam(name = "isValid", required = false) boolean validity,
                             @RequestParam(name = "relation") String relation, @RequestParam(name = "group", required = false) Integer groupId,
                             @RequestParam(name = "tariffs", required = false) boolean tariff) {
       optionService.SetOptionGroup(option, groupId);
        if (!relation.equals("nothing")) {
            if (relation.equals("alone")) {
                optionService.removeRelations(option);
            } else {
                if (relation.equals("parent")) {
                    Set<OptionDTO> parent = new HashSet<>();
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
        if (tariff) {
            tariffPage(model, option);
            return "tariffs";
        }
        optionService.editOption(option);
        return "redirect:/options";
    }

    @PostMapping("/edit/parent")
    public String existingOptionParent(Model model, @ModelAttribute(value = "option") OptionDTO option, @RequestParam(name = "optionID2", required = false) Integer id, @RequestParam(name = "action") boolean tariff) {
       optionService.SetParent(option, id);
        if (tariff) {
            tariffPage(model, option);
            return "tariffs";
        }
        optionService.editOption(option);
        return "redirect:/options";
    }



    @PostMapping("/edit/children")
    public String existingOptionChildren(Model model, @ModelAttribute(value = "option") OptionDTO option, @RequestParam(name = "optionID2", required = false) List<Integer> id,
                                         @RequestParam(name = "action") boolean tariff) {
        optionService.SetChildren(option, id);
        if (tariff) {
            tariffPage(model, option);
            return "tariffs";
        }
        optionService.editOption(option);
        return "redirect:/options";
    }

    @PostMapping("/edit/tariffs")
    public String existingOptionTariffs(@ModelAttribute(value = "option") OptionDTO option, @RequestParam(name = "tariffID2", required = false) List<Integer> id) {
        optionService.SetCompatibleTariffs(option, id);
        optionService.editOption(option);
        return "redirect:/options";
    }


    @GetMapping("/delete/{id}")
    public String deleteOption(@PathVariable(value = "id") Integer id) {
        optionService.deleteOption(id);
        return "redirect:/options";
    }
}
