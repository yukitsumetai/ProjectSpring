package com.telekom.controller;

import com.telekom.entityDTO.*;
import com.telekom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes(types = ContractDTO.class)
@RequestMapping(value = "/newContract")
public class ContractController {

    @Autowired
    private TariffService tariffService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Autowired
    private ContractService contractService;

    @GetMapping("/tariffs")
    public String start(Model model) {
        ContractDTO contract = new ContractDTO();
        model.addAttribute(contract);
        model.addAttribute("table", "add");
        model.addAttribute("tariffs", tariffService.getAllValid());
        return "tariffs";
    }


    @PostMapping("options")
    public String newContractTariffAdd(Model model, ContractDTO contract, @RequestParam(name = "tariffID2") Integer id) {
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
    public String newContractOptionAdd(Model model, ContractDTO contract, @RequestParam(name = "action") String action, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        if (!contractService.setOptions(contract, id, false)) {
            model.addAttribute("message", "Options are not compatible");
            return "error";
        }
        model.addAttribute("table", "add");
        if (action.equals("new")) {
            ClientDTO client = new ClientDTO();
            AddressDTO a = new AddressDTO();
            client.setAddress(a);
            model.addAttribute("client", client);
            model.addAttribute("numbers", phoneNumberService.getAll());
            return "newClient";
        } else {
            return "clients";
        }
    }

    @PostMapping("/confirmExisting")
    public String newContractOptionAdd(Model model, ContractDTO contract, @RequestParam(name = "clientID2") Integer id, @RequestParam String phoneNumber) {
        model.addAttribute("table", "add");
        contract.setClient(clientService.getOne(id));
        contract.setPhoneNumber(phoneNumber);
        return "contract";
    }

    @PostMapping("/confirm")
    public String newContractOptionAdd(Model model, ContractDTO contract, @ModelAttribute @Valid ClientDTO client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Operation cannot be proceeded further because received data contains some errors");
            return "contract";
        }
        model.addAttribute("table", "add");
        contract.setClient(client);
        contract.setPhoneNumber(client.getPhoneNumber());
        return "contract";
    }

    @PostMapping("/confirmExisting/true")
    public String confirmationExisting(ContractDTO contract) {
        contractService.add(contract);
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

    @PostMapping("/confirm/true")
    public String confirmation(ContractDTO contract) {
        contractService.add(contract);
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

}
