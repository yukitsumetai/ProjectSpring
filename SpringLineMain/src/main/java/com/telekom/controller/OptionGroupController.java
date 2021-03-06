package com.telekom.controller;

import com.telekom.model.dto.OptionGroupDto;
import com.telekom.service.api.OptionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@SessionAttributes(types = OptionGroupDto.class)
@RequestMapping(value = "/optionGroups")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
public class OptionGroupController {
    private static final String OPTION_GROUP_PAGE ="redirect:/optionGroups";

    @Autowired
    private OptionGroupService optionGroupService;

    @GetMapping("/new")
    public String newOptionGroup(Model model){
        OptionGroupDto optionGroup=new OptionGroupDto();
        model.addAttribute(optionGroup);
        return "addOptionGroup";
    }

    @PostMapping("/new")
    public String newTariffAdd(Model model, @Valid OptionGroupDto optionGroup, BindingResult bindingResult, @RequestParam(name = "isValid", required=false) boolean validity,
                               @RequestParam(name="compatibleOptions", required=false) boolean options, SessionStatus status) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Tariff was not added because received data contains some errors");
            return "addOptionGroup";
        } else{
        if (!validity) optionGroup.setIsValid(false);
        if(options){
            model.addAttribute("table", "optionGroupAdd");
            model.addAttribute("group", true); //including invalid
            return "options";
        }
        optionGroupService.add(optionGroup);
        status.setComplete();
        return OPTION_GROUP_PAGE;}
    }
    @PostMapping("/new/options")
    public String newTariffOptions(OptionGroupDto optionGroup, @RequestParam(name = "optionID2", required = false) List<Integer> id, SessionStatus status) {
        optionGroupService.setOptionsDto(optionGroup, id);
        optionGroupService.add(optionGroup);
        status.setComplete();
        return OPTION_GROUP_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(Model model, @PathVariable(value = "id") Integer id) {
        OptionGroupDto optionGroup = optionGroupService.getOptionGroup(id);
        model.addAttribute("optionGroup", optionGroup);
        return "editOptionGroup";
    }

    @PostMapping("/edit")
    public String editProduct(Model model, @ModelAttribute(value = "optionGroup") OptionGroupDto optionGroup,
                              @RequestParam(name = "compatibleOptions", required = false) boolean options, SessionStatus status) {
        if (options) {
            model.addAttribute("table", "optionGroupEdit");
            model.addAttribute("existing", optionGroup.getOptions());
            model.addAttribute("id", optionGroup.getId());
            model.addAttribute("group", true);
            return "options";
        }
        optionGroupService.editOptionGroup(optionGroup);
        status.setComplete();
        return OPTION_GROUP_PAGE;
    }

    @PostMapping("/edit/options")
    public String editOptionGroupOptions(@ModelAttribute(value = "optionGroup") OptionGroupDto optionGroup,
                                         @RequestParam(name = "optionID2", required = false) List<Integer> id, SessionStatus status) {
        optionGroupService.setOptionsDto(optionGroup, id);
        optionGroupService.editOptionGroup(optionGroup);
        status.setComplete();
        return OPTION_GROUP_PAGE;
    }

    @GetMapping("/delete/{id}")
    public String deleteOptionGroup(@PathVariable(value = "id") Integer id) {
        optionGroupService.deleteOptionGroup(id);
        return OPTION_GROUP_PAGE;
    }

}
