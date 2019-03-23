package com.telekom.controller;

import com.telekom.entity.Option;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.TariffDTO;
import com.telekom.service.OptionService;
import com.telekom.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
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
        } else if (tariff) {
            model.addAttribute("table", "option");
            model.addAttribute("tariffs", tariffService.getAllValid());
            return "tariffs";
        }
        optionService.add(option);
        return "redirect:/options";
    }

    @PostMapping("/new/parent")
    public String newOptionParent(Model model, OptionDTO option, @RequestParam(name = "optionID") Integer id, @RequestParam(name = "action") boolean tariff) {
        OptionDTO o = new OptionDTO();
        o.setId(id);
        option.setParent(o);
        if (tariff) {
            model.addAttribute("table", "option");
            model.addAttribute("tariffs", tariffService.getAllValid());
            return "tariffs";
        }
        optionService.add(option);
        return "redirect:/options";
    }

    @PostMapping("/new/children")
    public String newOptionChildren(Model model, OptionDTO option, @RequestParam(name = "optionID") List<Integer> id,
                                    @RequestParam(name = "action") boolean tariff) {
        Set<OptionDTO> children = new HashSet<>();
        for (Integer i : id) {
            OptionDTO o = new OptionDTO();
            o.setId(i);
            children.add(o);
        }
        option.setChildren(children);
        if (tariff) {
            model.addAttribute("table", "option");
            model.addAttribute("tariffs", tariffService.getAll());
            return "tariffs";
        }
        optionService.add(option);
        return "redirect:/options";
    }

    @PostMapping("/new/tariffs")
    public String newOptionTariffs(OptionDTO option, @RequestParam(name = "tariffID") List<Integer> id) {
        Set<TariffDTO> tariffs = new HashSet<>();
        for (Integer i : id) {
            TariffDTO t = new TariffDTO();
            t.setId(i);
            tariffs.add(t);
        }
        option.setCompatibleTariffs(tariffs);
        optionService.add(option);
        return "redirect:/options";
    }


    @GetMapping("/edit/{id}")
    public ModelAndView getEditOption(@PathVariable(value = "id") Integer id) {
        OptionDTO option = optionService.getOne(id);
        return new ModelAndView("proxy", "option", option);
    }

    @PostMapping("/edit")
    public String editOption(@ModelAttribute(value = "option") OptionDTO option, @RequestParam(name = "isValid", required = false) boolean validity) {
        if (!validity) option.setIsValid(false);
        optionService.editOption(option);
        return "redirect:/options";
    }

    @GetMapping("/delete/{id}")
    public String deleteOption(@PathVariable(value = "id") Integer id) {
        optionService.deleteOption(id);
        return "redirect:/options";
    }
}
