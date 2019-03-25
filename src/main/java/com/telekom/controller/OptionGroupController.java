package com.telekom.controller;

import com.telekom.entityDTO.OptionGroupDTO;
import com.telekom.service.OptionGroupService;
import com.telekom.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SessionAttributes(types = OptionGroupDTO.class)
@RequestMapping(value = "/optionGroups")
@Controller
public class OptionGroupController {
    @Autowired
    private OptionService optionService;
    @Autowired
    private OptionGroupService optionGroupService;

    @GetMapping("/new")
    public String newOptionGroup(Model model){
        OptionGroupDTO optionGroup=new  OptionGroupDTO();
        model.addAttribute("optionGroup", optionGroup);
        return "addOptionGroup";
    }

    @PostMapping("/new")
    public String newTariffAdd(Model model, OptionGroupDTO optionGroup, @RequestParam(name = "isValid", required=false) boolean validity,
                               @RequestParam(name="compatibleOptions", required=false) boolean options) {
        if (!validity) optionGroup.setIsValid(false);
        if(options){
            model.addAttribute("table", "optionGroupAdd");
            model.addAttribute("options", optionService.getAllNoParentNoGroup());
            return "options";
        }
        optionGroupService.add(optionGroup);
        return "redirect:/optionGroups";
    }
    @PostMapping("/new/options")
    public String newTariffOptions(OptionGroupDTO optionGroup, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        optionGroupService.SetOptions(optionGroup, id);
        optionGroupService.add(optionGroup);
        return "redirect:/optionGroups";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(Model model, @PathVariable(value = "id") Integer id) {
        OptionGroupDTO optionGroup = optionGroupService.getOne(id);
        model.addAttribute("optionGroup", optionGroup);
        return "editOptionGroup";
    }

    @PostMapping("/edit")
    public String editProduct(Model model, @ModelAttribute(value = "optionGroup") OptionGroupDTO optionGroup, @RequestParam(name = "compatibleOptions", required = false) boolean options) {
        if (options) {
            model.addAttribute("table", "optionGroupEdit");
            model.addAttribute("existingOptions", optionGroup.getOptions());
            model.addAttribute("options", optionService.getAllNoParentNoGroup());
            return "options";
        }
        optionGroupService.editOptionGroup(optionGroup);
        return "redirect:/optionGroups";
    }

    @PostMapping("/edit/options")
    public String editOptionGroupOptions(@ModelAttribute(value = "optionGroup") OptionGroupDTO optionGroup, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        optionGroupService.SetOptions(optionGroup, id);
        optionGroupService.editOptionGroup(optionGroup);
        return "redirect:/optionGroups";
    }

    @PostMapping("/delete/{id}")
    public String deleteOptionGroup(@PathVariable(value = "id") Integer id) {
        optionGroupService.deleteOptionGroup(id);
        return "redirect:/optionGroups";
    }

}
