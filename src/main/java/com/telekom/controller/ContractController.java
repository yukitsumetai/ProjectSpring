package com.telekom.controller;

import com.telekom.entityDTO.*;
import com.telekom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(types = ContractDTO.class, value = "table")
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
        model.addAttribute("tariffs", tariffService.getAll());
        return "tariffs";
    }


    @PostMapping("/options")
    public String newContractTariffAdd(Model model, ContractDTO contract, @RequestParam(name = "tariffID") Integer id) {

        TariffDTO tmp = tariffService.getOne(id); //нужно ли тянуть тариф опять из базы?
        contract.setTariff(tmp);
        contract.setPrice(tmp.getPrice());
        model.addAttribute("options", tmp.getOptions());
        return "options";
    }

    @RequestMapping(value = "/view", params = "Customer", method = RequestMethod.POST)
    public String action1() {
        return "index";
    }

    @RequestMapping(value = "/view", params = "Shop", method = RequestMethod.POST)
    public String action2() {
        return "redirect:/shopagent";
    }


    @PostMapping(value="/client")
    public String newContractOptionAdd(Model model, ContractDTO contract,@RequestParam(name="action") String action, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        if (id != null) {
            List<OptionDTO> options = new ArrayList<>();
            for (Integer i : id
            ) {
                OptionDTO tmp = optionService.getOne(i);
                options.add(tmp);
                contract.setPrice(tmp.getPriceMonthly());
                contract.setPriceOneTime(tmp.getPriceOneTime());
            }
            contract.setOptions(options);
        }
        ClientDTO client = new ClientDTO();
        AddressDTO a = new AddressDTO();
        ContractDTO c =new ContractDTO();
        client.setAddress(a);
        model.addAttribute("client", client);
       if(action.equals("new")){
           model.addAttribute("numbers", phoneNumberService.getAll());
           return "newClient";}
       else {
           model.addAttribute("clients", clientService.getAll());
           return "clients";
       }
    }


    @PostMapping("/confirmExisting")
    public String newContractOptionAdd(ContractDTO contract,  @RequestParam(name = "clientID", required = false) Integer id) {

        contract.setClient(clientService.getOne(id));
        contract.setPhoneNumber("46576685434");
        return "confirmation";
    }

    @PostMapping("/confirm")
    public String newContractOptionAdd(ContractDTO contract, @ModelAttribute ClientDTO client) {
        contract.setClient(client);
        contract.setPhoneNumber(client.getPhoneNumber());
        return "confirmation";
    }

    @PostMapping("/confirm/true")
    public String confirmation(ContractDTO contract, SessionStatus status) {
        contractService.add(contract);
        status.setComplete();
        return "redirect:/users";
    }

}
