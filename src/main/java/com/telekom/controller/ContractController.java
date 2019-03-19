package com.telekom.controller;

import com.telekom.entityDTO.*;
import com.telekom.service.ContractService;
import com.telekom.service.OptionService;
import com.telekom.service.PhoneNumberService;
import com.telekom.service.TariffService;
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


    @PostMapping("/client")
    public String newContractOptionAdd(Model model, ContractDTO contract, @RequestParam(name = "optionID", required = false) List<Integer> id) {
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
        client.setAddress(a);
        model.addAttribute("numbers", phoneNumberService.getAll());
        model.addAttribute("client", client);

        return "newClient";
    }

    @PostMapping("/confirm")
    public String newContractOptionAdd(ContractDTO contract, @ModelAttribute ClientDTO client) {
        contract.setClient(client);
        // BigInteger phoneNumber=new BigInteger(); //do check!!
        //contract.setPhoneNumber(number);
        return "confirmation";
    }

    @PostMapping("/confirm/true")
    public String confirmation(ContractDTO contract, SessionStatus status) {
        contractService.add(contract);
        status.setComplete();
        return "redirect:/users";
    }

}
