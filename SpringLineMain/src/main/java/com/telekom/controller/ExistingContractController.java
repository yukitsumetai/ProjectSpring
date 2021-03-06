package com.telekom.controller;

import com.telekom.model.dto.ContractDto;
import com.telekom.model.dto.OptionDto;
import com.telekom.service.api.ContractService;
import com.telekom.service.api.OptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@SessionAttributes(types = ContractDto.class)
@RequestMapping(value = "/existingContract")
public class ExistingContractController {
public static final String TABLE = "table";
public static final String MESSAGE = "message";
public static final String EXISTING_CONTRACT = "redirect:/existingContract/";
public static final String ERROR = "error";

    @Autowired
    ContractService contractService;

    @Autowired
    OptionService optionService;

    @Autowired
    Logger logger;


    @GetMapping("/{id}")
    public ModelAndView getEditForm(ContractDto contract, @PathVariable(value = "id") String id) {
        logger.info("Getting contract "+ id);
        ContractDto contract2 = contractService.getOne(id);
        return new ModelAndView("contract", "contractDto", contract2);
    }

    /**
     * When NEB no - this is the last step in the process (no choosing options)
     * @param model
     * @param contract
     * @return
     */
    @GetMapping("/tariffs")
    public String tariffChoose(Model model, ContractDto contract) {
        logger.info("Changing tariff, contract is blocked - "+ contract.isBlocked());
        if (!contract.isBlocked()) {
            model.addAttribute("tariffs", contractService.getTariffsForAdd(contract));
            model.addAttribute(TABLE, "add");
            model.addAttribute("NEB", "no");
            contract.setTariff(null);
            contract.setOptions(new HashSet<>());
            contract.setPrice(0.00);
            return "tariffs";
        } else return null;
    }

    @PostMapping("/tariffs")
    public String tariffAdd(Model model, ContractDto contract, @RequestParam(name = "tariffID2")Integer id) {
        if(!contractService.setTariff(contract, id)){
            model.addAttribute(MESSAGE, "Tariff is not valid");
            return ERROR;
        }
        return "redirect:/existingContract/confirm";
    }

    @GetMapping("/confirm")
    public String confirm(Model model) {
        model.addAttribute(TABLE, "add");
        return "contract";
    }

    @PostMapping("/confirm/true")
    public String confirmation(Model model, ContractDto contract, SessionStatus status,
                               @RequestParam(required = false) Boolean email) {
        contractService.update(contract);
        if (email!=null) {
            boolean correct = contractService.sendEmail(false, contract);
            if (!correct) {
                model.addAttribute(MESSAGE, "Email was not sent");
                return ERROR;
            }
        }
        status.setComplete();
        return EXISTING_CONTRACT + contract.getPhoneNumber();
    }

    /**
     * We need option groups, parent options and children options so they could be groupped correctly on jsp page
     * @param model
     * @param contract
     * @return
     */
    @GetMapping("/options")
    public String addOptionsChoose(Model model, ContractDto contract) {
        logger.info("Changing options, contract is blocked - "+ contract.isBlocked());
        if (!contract.isBlocked()) {
            Set<OptionDto> options = contractService.getOptionsParents(contract);
            if (options.isEmpty()) {
                model.addAttribute(MESSAGE , "No options are available"); //add message
            } else {
                model.addAttribute("options", contractService.getParentsForExistingContract(contract));
                model.addAttribute("optionGroups", contractService.getGroups(contract));
                model.addAttribute("children", contractService.getChildrenForExistingContract(contract));
                model.addAttribute(TABLE, "add");
            }
            return "contractOptions";
        } else return null;
    }

    @PostMapping("/options")
    public String addOptions(Model model, ContractDto contract, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        if (!contractService.setOptions(contract, id, true)){
            model.addAttribute(MESSAGE , "Options are not compatible");
            return ERROR;
        }
        return "redirect:/existingContract/confirm";
    }

    @GetMapping("/block")
    public String block(ContractDto contract, HttpServletRequest request, SessionStatus status) {
        boolean admin = false;
        if (request.isUserInRole("ADMIN")) {
            admin = true;
        }
        contractService.block(contract, admin);
        status.setComplete();
        return EXISTING_CONTRACT + contract.getPhoneNumber();
    }

    @GetMapping("/unblock")
    public String unblock(ContractDto contract, HttpServletRequest request, SessionStatus status) {
        boolean admin = false;
        if (request.isUserInRole("ADMIN")) {
            admin = true;
        }
        contractService.unblock(contract, admin);
        status.setComplete();
        return EXISTING_CONTRACT + contract.getPhoneNumber();
    }


}
