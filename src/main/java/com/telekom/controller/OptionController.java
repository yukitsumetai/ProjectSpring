package com.telekom.controller;

import com.telekom.entityDTO.OptionDTO;
import com.telekom.service.OptionService;
import com.telekom.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        OptionDTO option = new OptionDTO();
        model.addAttribute("option", option);
        model.addAttribute("table", "add");
        model.addAttribute("tariffs", tariffService.getAll());
        return "addOption";
    }

    @PostMapping("/new")
    public String newOptionAdd(Model model, OptionDTO option, @RequestParam(name = "opt", required = false) List<Integer> tariffs,
                               @RequestParam(name = "isValid", required = false) boolean validity,
                               @RequestParam(name = "relation") String relation, @RequestParam(name = "group", required = false) boolean group, @RequestParam(name = "tariffs", required = false) boolean tariff) {
        if (!validity) option.setIsValid(false);
        if (!relation.equals("alone")) {
            if (relation.equals("parent")) model.addAttribute("options", optionService.getAllNoParent());
            else model.addAttribute("options", optionService.getAllNoChildrenAndParent());
            model.addAttribute("relation", relation);
            model.addAttribute("tariff", tariff);
            return "optionsRelations";
        }
        if (tariff) {
            model.addAttribute("table", "option");
            model.addAttribute("tariffs", tariffService.getAllValid());
            return "tariffs";
        }
        optionService.add(option);
        return "redirect:/options";
    }

    @PostMapping("/new/parent")
    public String newOptionParent(Model model, OptionDTO option, @RequestParam(name = "optionID", required = false) Integer id, @RequestParam(name = "action") boolean tariff) {
        optionService.SetParent(option, id);
        if (tariff) {
            model.addAttribute("table", "option");
            model.addAttribute("tariffs", tariffService.getAllValid());
            return "tariffs";
        }
        optionService.add(option);
        return "redirect:/options";
    }

    @PostMapping("/new/children")
    public String newOptionChildren(Model model, OptionDTO option, @RequestParam(name = "optionID", required = false) List<Integer> id,
                                    @RequestParam(name = "action") boolean tariff) {
       optionService.SetChildren(option, id);
        if (tariff) {
            model.addAttribute("table", "option");
            model.addAttribute("tariffs", tariffService.getAll());
            return "tariffs";
        }
        optionService.add(option);
        return "redirect:/options";
    }

    @PostMapping("/new/tariffs")
    public String newOptionTariffs(OptionDTO option, @RequestParam(name = "tariffID", required = false) List<Integer> id) {
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

    @PostMapping("/edit")
    public String editOption(Model model, @ModelAttribute(value = "option") OptionDTO option, @RequestParam(name = "isValid", required = false) boolean validity,
                             @RequestParam(name = "relation") String relation, @RequestParam(name = "group", required = false) boolean group, @RequestParam(name = "tariffs", required = false) boolean tariff) {
        if (!validity) option.setIsValid(false);
        if (!relation.equals("nothing")) {
            if (relation.equals("alone")) {
                optionService.removeRelations(option);
            } else {
                List<OptionDTO> all = new ArrayList<>();
                Set<OptionDTO> existing = new HashSet<>();
                if (relation.equals("parent")) {
                    all = optionService.getAllNoParent();
                    model.addAttribute("options", all);
                    existing.add(option.getParent());// to show on FE
                    model.addAttribute("relation", "parentEdit");
                } else {
                    existing = option.getChildren();
                    all = optionService.getAllNoChildrenAndParent();
                    all.addAll(existing);
                    model.addAttribute("relation", "childrenEdit");
                }
                model.addAttribute("existingOptions", existing);
                optionService.removeRelations(option);
                all.removeIf(st -> st.getId() == option.getId());
                model.addAttribute("options", all);
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
    public String existingOptionParent(Model model, @ModelAttribute(value = "option") OptionDTO option, @RequestParam(name = "optionID", required = false) Integer id, @RequestParam(name = "action") boolean tariff) {
       optionService.SetParent(option, id);
        if (tariff) {
            tariffPage(model, option);
            return "tariffs";
        }
        optionService.editOption(option);
        return "redirect:/options";
    }

    private void tariffPage(Model model, OptionDTO option) {
        model.addAttribute("table", "optionEdit");
        model.addAttribute("tariffs", tariffService.getAllValid());
        model.addAttribute("existingTariffs", option.getCompatibleTariffs());
    }

    @PostMapping("/edit/children")
    public String existingOptionChildren(Model model, @ModelAttribute(value = "option") OptionDTO option, @RequestParam(name = "optionID", required = false) List<Integer> id,
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
    public String existingOptionTariffs(@ModelAttribute(value = "option") OptionDTO option, @RequestParam(name = "tariffID", required = false) List<Integer> id) {
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
