package com.telekom.controller;


import com.telekom.entityDTO.ClientDTO;
import com.telekom.entityDTO.ContractDTO;

import com.telekom.entityDTO.OptionDTO;
import com.telekom.service.ContractService;
import com.telekom.service.OptionService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes(types = ContractDTO.class)
@RequestMapping(value = "/existingContract")

public class ExistingContractController {
    @Autowired
    ContractService contractService;

    @Autowired
    OptionService optionService;


    @GetMapping("/{id}")
    public ModelAndView getEditForm(ContractDTO contract, @PathVariable(value = "id") String id) {



        contract = contractService.getOne(id);
        if (contract == null) {
            return new ModelAndView("search", "message", "Contract not found");
        }
        return new ModelAndView("contract", "contractDTO", contract);
    }

    @PreAuthorize("contractDTO.blocked == false")
    @GetMapping("/tariffs")
    public String tariffChoose(Model model, ContractDTO contract) {
        model.addAttribute("tariffs", contractService.getTariffsForAdd(contract));
        model.addAttribute("table", "add");
        model.addAttribute("NEB", "no");
        contract.setTariff(null);
        contract.setOptions(null);
        contract.setPrice(0.00);
        return "tariffs";
    }

    @PostMapping("/tariffs")
    public String tariffAdd(ContractDTO contract, @RequestParam(name = "tariffID") Integer id) {
        contract.setOptions(null);
        contractService.setTariff(contract, id);
        return "redirect:/existingContract/confirm";
    }

    @GetMapping("/confirm")
    public String confirm(Model model) {
        model.addAttribute("table", "add");
        return "contract";
    }

    @PostMapping("/confirm/true")
    public String confirmation(ContractDTO contract) {
        contractService.update(contract);
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

    @GetMapping("/options")
    public String addOptionsChoose(Model model, ContractDTO contract) {
        Set<OptionDTO> options = contractService.getOptions(contract);
        if (!(options.size() > 0)) {
            model.addAttribute("message", "No options are available"); //add message
        } else {
            model.addAttribute("options", contractService.getParentsForExisting(contract));
            model.addAttribute("optionGroups", contractService.getGroups(contract));
            model.addAttribute("children", contractService.getChildrenForExisting(contract));
            model.addAttribute("table", "add");
           // contract.setOptions(null);
        }
        return "contractOptions";
    }

    @PostMapping("/options")
    public String addOptions(ContractDTO contract, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        contractService.setOptions(contract, id);
        return "redirect:/existingContract/confirm";
    }

    @GetMapping("/block")
    public String block(Model model, ContractDTO contract, HttpServletRequest request) {
       boolean admin=false;
        if (request.isUserInRole("ADMIN")) {
          admin=true;
        }
        contractService.block(contract, admin);
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

    @GetMapping("/unblock")
    public String unblock(ContractDTO contract, HttpServletRequest request) {
        boolean admin=false;
        if (request.isUserInRole("ADMIN")) {
            admin=true;
        }
        contractService.unblock(contract, admin);
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }


}
