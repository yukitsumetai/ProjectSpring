package com.telekom.controller;

import com.telekom.model.dto.OptionDto;
import com.telekom.service.api.OptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SessionAttributes(types = OptionDto.class)
@RequestMapping(value = "/options")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
public class OptionController {
    private static final String OPTIONS = "redirect:/options";
    private static final String EXISTING = "existing";
    private static final String PARENT = "parent";

    @Autowired
    private OptionService optionService;
    @Autowired
    private Logger logger;

    @GetMapping("/new")
    public String newOption(Model model) {
        OptionDto option = new OptionDto();
        model.addAttribute(option);
        return "addOption";
    }

    @PostMapping("/new")
    public String newOptionAdd(Model model, @Valid OptionDto option, BindingResult bindingResult, @RequestParam(name = "isValid", required = false) boolean validity,
                               @RequestParam(name = "relation") String relation, @RequestParam(name = "group", required = false) Integer groupId,
                               @RequestParam(name = "tariffs", required = false) boolean tariff, SessionStatus status) {
        logger.info("Creating new option: valid - " + validity + ", relation - " + relation + ", group - " + groupId + ", tariffs - " + tariff);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Option was not added because received data contains some errors");
            return "addOption";
        } else {
            if (!validity) option.setIsValid(false);
            optionService.setOptionGroup(option, groupId);
            if (!relation.equals("alone")) {
                boolean flag = false;
                if (relation.equals(PARENT)) flag = true;
                model.addAttribute(PARENT, flag);
                model.addAttribute("tariff", tariff);
                return "optionsRelations";
            }
            return setTariffForOption(model, tariff, option, status);
        }
    }

    private String setTariffForOption(Model model, boolean tariff, OptionDto option, SessionStatus status) {
        if (tariff) {
            logger.info("Setting tariffs for new optionDto " + option);
            model.addAttribute("table", "option");
            return "tariffs";
        }
        optionService.add(option);
        status.setComplete();
        return OPTIONS;
    }

    @PostMapping("/new/parent")
    public String newOptionParent(Model model, OptionDto option, @RequestParam(name = "optionID2", required = false) Integer id,
                                  @RequestParam(name = "action") boolean tariff, SessionStatus status) {
        optionService.setParent(option, id);
        return setTariffForOption(model, tariff, option, status);
    }

    @PostMapping("/new/children")
    public String newOptionChildren(Model model, OptionDto option, @RequestParam(name = "optionID2", required = false) List<Integer> id,
                                    @RequestParam(name = "action") boolean tariff, SessionStatus status) {
        optionService.setChildren(option, id);
        return setTariffForOption(model, tariff, option, status);
    }

    @PostMapping("/new/tariffs")
    public String newOptionTariffs(OptionDto option, @RequestParam(name = "tariffID2", required = false) List<Integer> id) {
        optionService.setCompatibleTariffs(option, id);
        optionService.add(option);
        return OPTIONS;
    }

    @GetMapping("/edit/{id}")
    public String getEditOption(Model model, @PathVariable(value = "id") Integer id) {
        logger.info("Editing option " + id);
        OptionDto option = optionService.getOne(id);
        model.addAttribute("option", option);
        return "editOption";
    }


    @PostMapping("/edit")
    public String editOption(Model model, @ModelAttribute(value = "option") OptionDto option, @RequestParam(name = "isValid", required = false) boolean validity,
                             @RequestParam(name = "relation") String relation, @RequestParam(name = "group", required = false) Integer groupId,
                             @RequestParam(name = "tariffs", required = false) boolean tariff, SessionStatus status) {
        logger.info("Creating new option: valid - " + validity + ", relation - " + relation + ", group - " + groupId + ", tariffs - " + tariff);
        optionService.setOptionGroup(option, groupId);
        if (!relation.equals("nothing")) {
            if (relation.equals("alone")) {
                optionService.removeRelations(option);
            } else {
                if (relation.equals(PARENT)) {
                    Set<OptionDto> parent = new HashSet<>();
                    parent.add(option.getParent());
                    model.addAttribute(EXISTING, parent);
                    model.addAttribute(PARENT, true);
                } else {
                    model.addAttribute(PARENT, false);
                    model.addAttribute(EXISTING, option.getChildren());
                }
                model.addAttribute("optionId", option.getId());
                optionService.removeRelations(option);
                model.addAttribute("tariff", tariff);
                return "optionsRelations";
            }
        }
        return editTariffOrOption(tariff, model, option, status);
    }

    @PostMapping("/edit/parent")
    public String existingOptionParent(Model model, @ModelAttribute(value = "option") OptionDto option,
                                       @RequestParam(name = "optionID2", required = false) Integer id,
                                       @RequestParam(name = "action") boolean tariff, SessionStatus status) {
        optionService.setParent(option, id);
        return editTariffOrOption(tariff, model, option, status);
    }


    @PostMapping("/edit/children")
    public String existingOptionChildren(Model model, @ModelAttribute(value = "option") OptionDto option,
                                         @RequestParam(name = "optionID2", required = false) List<Integer> id,
                                         @RequestParam(name = "action") boolean tariff, SessionStatus status) {
        optionService.setChildren(option, id);
        return editTariffOrOption(tariff, model, option, status);
    }

    @PostMapping("/edit/tariffs")
    public String existingOptionTariffs(@ModelAttribute(value = "option") OptionDto option,
                                        @RequestParam(name = "tariffID2", required = false) List<Integer> id, SessionStatus status) {
        optionService.setCompatibleTariffs(option, id);
        optionService.editOption(option);
        status.setComplete();
        return OPTIONS;
    }


    @GetMapping("/delete/{id}")
    public String deleteOption(@PathVariable(value = "id") Integer id) {
        optionService.deleteOption(id);
        return OPTIONS;
    }




    private String editTariffOrOption(Boolean tariff, Model model, OptionDto option, SessionStatus status) {
        if (tariff) {
            logger.info("Setting tariffs for existing optionDto " + option);
            model.addAttribute("table", "optionEdit");
            model.addAttribute("id", option.getId());
            model.addAttribute(EXISTING, option.getCompatibleTariffs());
            return "tariffs";
        }
        optionService.editOption(option);
        status.setComplete();
        return OPTIONS;
    }
}
