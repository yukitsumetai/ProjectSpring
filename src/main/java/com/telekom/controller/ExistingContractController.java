package com.telekom.controller;

import com.telekom.dao.ClientDAO;
import com.telekom.entityDTO.ContractDTO;
import com.telekom.entityDTO.OptionDTO;
import com.telekom.entityDTO.TariffDTO;
import com.telekom.service.ContractService;
import com.telekom.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(types = ContractDTO.class)
@RequestMapping(value = "/existingContract")

public class ExistingContractController {
    @Autowired
    ContractService contractService;

    @Autowired
    OptionService optionService;




    @GetMapping("/tariffChange")
    public String tariffChoose(Model model, ContractDTO contract) {
        model.addAttribute("tariffs", contractService.getTariffsForAdd(contract));
        model.addAttribute("table", "add");
        model.addAttribute("NEB", "no");
        return "tariffs";
    }

    @PostMapping("/tariffChange")
    public String tariffAdd(Model model, ContractDTO contract,  @RequestParam(name = "tariffID") Integer id, SessionStatus status) {
        contract.setOptions(null);
       contractService.setTariffAndUpdate(contract, id);
        status.setComplete();
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }


    @GetMapping("/optionsAdd")
    public String addOptionsChoose(Model model, ContractDTO contract) {
        model.addAttribute("options", contractService.getOptionsForAdd(contract));
        model.addAttribute("table", "add");
        return "options";
    }

    @PostMapping("/optionsAdd")
    public String addOptions(Model model, ContractDTO contract, @RequestParam(name = "optionID", required = false) List<Integer> id, SessionStatus status) {
        if (id != null) {
            contractService.setOptionsAndUpdate(contract, id);
        }
        status.setComplete();
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

    @GetMapping("/optionsDelete/{id}")
    public String showContract(ContractDTO contract, @PathVariable(value = "id") String id, SessionStatus status) {
        Integer n = Integer.valueOf(id);
        contract = contractService.deleteOption(contract, n);
        status.setComplete();
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

    @GetMapping("/{id}")
    public ModelAndView getEditForm(ContractDTO contract, @PathVariable(value = "id") String id) {
        contract = contractService.getOne(id);
        return new ModelAndView("contract", "contractDTO", contract);
    }
}
