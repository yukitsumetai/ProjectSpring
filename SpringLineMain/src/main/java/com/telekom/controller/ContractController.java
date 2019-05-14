package com.telekom.controller;

import com.telekom.model.dto.*;
import com.telekom.service.api.ClientService;
import com.telekom.service.api.ContractService;
import com.telekom.service.impl.ImageRecognitionImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes(types = ContractDto.class)
@RequestMapping(value = "/newContract")
public class ContractController {
    public static final String table = "table";
    public static final String message = "message";
    public static final String error = "error";
    public static final String TARIFFS = "redirect:/tariffs/";
    @Autowired
    private ClientService clientService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private Logger logger;

    @GetMapping("tariffs")
    public String start(Model model) {
        logger.info("Starting new contract");
        ContractDto contract = new ContractDto();
        model.addAttribute(contract);
        model.addAttribute(table, "add");
        return "tariffs";
    }

    @GetMapping("options")
    public String tariffsBack(SessionStatus status) {
        status.setComplete();
        return TARIFFS;
    }

    @PostMapping("options")
    public String newContractTariffAdd(Model model, ContractDto contract, @RequestParam(name = "tariffID2") Integer id) {
        logger.info("Setting tariff in new contract, tariff id " + id);
        if (!contractService.setTariff(contract, id)) {
            model.addAttribute(message, "Tariff is not valid");
            return error;
        }
        model.addAttribute("options", contractService.getOptionsParents(contract));
        model.addAttribute("optionGroups", contractService.getGroups(contract));
        model.addAttribute("children", contractService.getOptionsChildren(contract));
        model.addAttribute(table, "add");
        model.addAttribute("NEB", "yes");
        return "contractOptions";
    }

    @GetMapping("client")
    public String clientBack(SessionStatus status) {
        status.setComplete();
        return TARIFFS;
    }

    @PostMapping("client")
    public String newContractOptionAdd(Model model, ContractDto contract, @RequestParam(name = "action") String action, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        logger.info("Setting options and getting client for new contract, client " + action);
        if (!contractService.setOptions(contract, id, false)) {
            model.addAttribute(message, "Options are not compatible");
            return error;
        }
        model.addAttribute(table, "add");
        if (action.equals("new")) {
            ClientDto client = new ClientDto();
            AddressDto a = new AddressDto();
            client.setAddress(a);
            model.addAttribute("client", client);
            return "newClient";
        } else {
            return "clients";
        }
    }

    @GetMapping("confirmExisting")
    public String confirmExistingBack(SessionStatus status) {
        status.setComplete();
        return TARIFFS;
    }

    @PostMapping("/confirmExisting")
    public String newContractOptionAdd(Model model, ContractDto contract, @RequestParam(name = "clientID2") Integer id, @RequestParam String phoneNumber) {
        logger.info("Existing client, adding phone number" + phoneNumber);
        model.addAttribute(table, "add");
        contract.setClient(clientService.getClient(id));
        contract.setPhoneNumber(phoneNumber);
        return "contract";
    }

    @GetMapping("confirm")
    public String confirmBack(SessionStatus status) {
        status.setComplete();
        return TARIFFS;
    }

    @PostMapping("/confirm")
    public String newContractOptionAdd(Model model, ContractDto contract, @ModelAttribute @Valid ClientDto client, BindingResult bindingResult) {
        logger.info("New client, adding phone number" + client.getPhoneNumber());
        if (bindingResult.hasErrors()) {
            model.addAttribute(message, "Operation cannot be proceeded further because received data contains some errors");
            return error;
        }
        model.addAttribute(table, "add");
        contract.setClient(client);
        contract.setPhoneNumber(client.getPhoneNumber());
        return "contract";
    }

    @GetMapping("/confirm/true")
    public String confirmTrueBack(SessionStatus status) {
        status.setComplete();
        return TARIFFS;
    }

    @PostMapping("/confirm/true")
    public String confirmation(Model model, ContractDto contract, @RequestParam(required = false) Boolean email, SessionStatus status) {
        contractService.add(contract);
        if (email!=null) {
            boolean correct = contractService.sendEmail(true, contract);
            if (!correct) {
                model.addAttribute(message, "Email was not sent");
                return error;
            }
        }
        status.setComplete();
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

    @GetMapping("/confirmExisting/true")
    public String confirmExistingTrueBack(SessionStatus status) {
        status.setComplete();
        return TARIFFS;
    }

    @PostMapping("/confirmExisting/true")
    public String confirmationExisting(Model model, ContractDto contract, @RequestParam(required = false) Boolean email, SessionStatus status) {
        contractService.add(contract);
        if (email!=null) {
            boolean correct = contractService.sendEmail(false, contract);
            if (!correct) {
                model.addAttribute(message, "Email was not sent");
                return error ;
            }
        }
        status.setComplete();
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }
}
