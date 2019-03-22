package com.telekom.controller;

import com.telekom.entityDTO.*;
import com.telekom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(types = ContractDTO.class)
@RequestMapping(value = "/newContract")

public class ContractController {

    @Autowired
    private TariffService tariffService;

    @Autowired
    private OptionService optionService;

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


    @PostMapping("/options")
    public String newContractTariffAdd(Model model, ContractDTO contract, @RequestParam(name = "tariffID") Integer id) {
        model.addAttribute("options", contractService.setTariff(contract, id)); //нужно ли тянуть тариф опять из базы?
        model.addAttribute("table", "add");
        model.addAttribute("NEB","yes");
        return "options";
    }



    @PostMapping(value="/client")
    public String newContractOptionAdd(Model model, ContractDTO contract,@RequestParam(name="action") String action, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        if (id != null) {
            contractService.setOptions(contract, id);
        }
        model.addAttribute("table", "add");
       if(action.equals("new")){
           ClientDTO client = new ClientDTO();
           AddressDTO a = new AddressDTO();
           client.setAddress(a);
           model.addAttribute("client", client);
           model.addAttribute("numbers", phoneNumberService.getAll());
           return "newClient";}
       else {
           model.addAttribute("clients", clientService.getAll());
           return "clients";
       }
    }


    @PostMapping("/confirmExisting")
    public String newContractOptionAdd(Model model, ContractDTO contract,  @RequestParam(name = "clientID") Integer id) {
        model.addAttribute("table", "add");
        contract.setClient(clientService.getOne(id));
        contract.setPhoneNumber("465766854347");
        return "contract";
    }

    @PostMapping("/confirm")
    public String newContractOptionAdd(Model model, ContractDTO contract, @ModelAttribute ClientDTO client) {
        model.addAttribute("table", "add");
        contract.setClient(client);
        contract.setPhoneNumber(client.getPhoneNumber());
        return "contract";
    }

    @PostMapping("/confirm/true")
    public String confirmation(ContractDTO contract, SessionStatus status) {
        contractService.add(contract);
        status.setComplete();
        return "redirect:/users";
    }

}
