package com.telekom.controller;

import com.telekom.model.dto.*;
import com.telekom.service.api.ClientService;
import com.telekom.service.api.ContractService;
import com.telekom.service.api.TariffService;
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
    private TariffService tariffService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ContractService contractService;

    @GetMapping("/tariffs")
    public String start(Model model) {
        ContractDto contract = new ContractDto();
        model.addAttribute(contract);
        model.addAttribute("table", "add");
        model.addAttribute("tariffs", tariffService.getAllValid());
        return "tariffs";
    }


    @PostMapping("options")
    public String newContractTariffAdd(Model model, ContractDto contract, @RequestParam(name = "tariffID2") Integer id) {
        if (!contractService.setTariff(contract, id)) {
            model.addAttribute("message", "Tariff is not valid");
            return "error";
        }
        model.addAttribute("options", contractService.getOptions(contract));
        model.addAttribute("optionGroups", contractService.getGroups(contract));
        model.addAttribute("children", contractService.getOptionsChildren(contract));
        model.addAttribute("table", "add");
        model.addAttribute("NEB", "yes");
        return "contractOptions";
    }

    @PostMapping("client")
    public String newContractOptionAdd(Model model, ContractDto contract, @RequestParam(name = "action") String action, @RequestParam(name = "optionID", required = false) List<Integer> id) {
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
        model.addAttribute("table", "add");
        contract.setClient(clientService.getOne(id));
        contract.setPhoneNumber(phoneNumber);
        return "contract";
    }

    @PostMapping("/confirm")
    public String newContractOptionAdd(Model model, ContractDto contract, @ModelAttribute @Valid ClientDto client, BindingResult bindingResult) {
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
    public String confirmation(ContractDto contract) {
        contractService.add(contract);
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

    @PostMapping("/confirmExisting/true")
    public String confirmationExisting(ContractDto contract) {
        contractService.add(contract);
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }


}
