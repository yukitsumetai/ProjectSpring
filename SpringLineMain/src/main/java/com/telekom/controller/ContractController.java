package com.telekom.controller;

import com.telekom.model.dto.*;
import com.telekom.service.api.ClientService;
import com.telekom.service.api.ContractService;
import com.telekom.service.api.TariffService;
import com.telekom.service.impl.ImageRecognitionImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes(types = ContractDto.class)
@RequestMapping(value = "/newContract")
public class ContractController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ContractService contractService;

    @Autowired
    Logger logger;

    @Autowired
    ImageRecognitionImpl imageRecognition;

    @GetMapping("/tariffs")
    public String start(Model model) {
        logger.info("Starting new contract");
        ContractDto contract = new ContractDto();
        model.addAttribute(contract);
        model.addAttribute("table", "add");
        return "tariffs";
    }


    @PostMapping("options")
    public String newContractTariffAdd(Model model, ContractDto contract, @RequestParam(name = "tariffID2") Integer id) {
        logger.info("Setting tariff in new contract, tariff id "+id);
        if (!contractService.setTariff(contract, id)) {
            model.addAttribute("message", "Tariff is not valid");
            return "error";
        }
        model.addAttribute("options", contractService.getOptionsParents(contract));
        model.addAttribute("optionGroups", contractService.getGroups(contract));
        model.addAttribute("children", contractService.getOptionsChildren(contract));
        model.addAttribute("table", "add");
        model.addAttribute("NEB", "yes");
        return "contractOptions";
    }

    @PostMapping("client")
    public String newContractOptionAdd(Model model, ContractDto contract, @RequestParam(name = "action") String action, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        logger.info("Setting options and getting client for new contract, client "+action);
        if (!contractService.setOptions(contract, id, false)) {
            model.addAttribute("message", "Options are not compatible");
            return "error";
        }
        model.addAttribute("table", "add");
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

    @PostMapping("/confirmExisting")
    public String newContractOptionAdd(Model model, ContractDto contract, @RequestParam(name = "clientID2") Integer id, @RequestParam String phoneNumber) {
        logger.info("Existing client, adding phone number"+phoneNumber);
        model.addAttribute("table", "add");
        contract.setClient(clientService.getOne(id));
        contract.setPhoneNumber(phoneNumber);
        return "contract";
    }

    @PostMapping("/confirm")
    public String newContractOptionAdd(Model model, ContractDto contract, @ModelAttribute @Valid ClientDto client, BindingResult bindingResult) {
        logger.info("New client, adding phone number"+client.getPhoneNumber());
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Operation cannot be proceeded further because received data contains some errors");
            return "error";
        }
        model.addAttribute("table", "add");
        contract.setClient(client);
        contract.setPhoneNumber(client.getPhoneNumber());
        return "contract";
    }

    @PostMapping("/confirm/true")
    public String confirmation(ContractDto contract, @RequestParam(required=false) Boolean email) {
        contractService.add(contract);
        if (email) contractService.sendPdf(true, contract);
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

    @PostMapping("/confirmExisting/true")
    public String confirmationExisting(Model model, ContractDto contract) {
        contractService.add(contract);
        if(!contractService.sendPdf(false, contract)){
            model.addAttribute("message", "Email was not sent");
            return "error";
        }
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

}
