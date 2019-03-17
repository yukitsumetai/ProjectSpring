package com.telekom.controller;


import com.telekom.entity.*;
import com.telekom.service.OptionService;
import com.telekom.service.PhoneNumberService;
import com.telekom.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(types = Contract.class)
@RequestMapping(value = "/newContract")

public class ContractController {

    @Autowired
    private TariffService tariffService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private PhoneNumberService phoneNumberService;

    @GetMapping("/tariffs")
    public String start(Model model) {

        Contract contract = new Contract();
        contract.setPrice((double) 10);
        model.addAttribute(contract);
        model.addAttribute("table", "add");
        model.addAttribute("tariffs", tariffService.getAll());
        return "tariffs";
    }


    @PostMapping("/options")
    public String newContractTariffAdd(Model model, Contract contract, @RequestParam(name = "tariffID") Integer id) {

        Tariff tmp = tariffService.getOne(id);
        contract.setTariff(tmp);
        contract.setPrice(tmp.getPrice());
        model.addAttribute("table", "add");
        model.addAttribute("options", tmp.getOptions());
        return "options";
    }


    @PostMapping("/client")
    public String newContractOptionAdd(Model model, Contract contract, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        List<Option> options = new ArrayList<>();
        for (Integer i : id
        ) {
            options.add(optionService.getOne(i));
        }
        // contract.setPrice(tmp.getPrice());
        Client client = new Client();
        Address ba = new Address();
        client.setAddress(ba);
        model.addAttribute("numbers", phoneNumberService.getAll());
        model.addAttribute("client", client);

        contract.setOptions(options);

        return "newClient";
    }

    @PostMapping("/confirm")
    public String newContractOptionAdd(Contract contract, @ModelAttribute Client client,
                                       @RequestParam(name = "phoneNumber") BigInteger number) {
        contract.setClient(client);
        contract.setPhoneNumber(number);
        return "Confirmation";
    }
}
