package com.telekom.controller;

import com.telekom.model.dto.*;
import com.telekom.service.api.ClientService;
import com.telekom.service.api.ContractService;
import com.telekom.service.impl.ImageRecognitionImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public static final String TABLE = "table";
    public static final String MESSAGE = "message";
    public static final String ERROR = "error";
    public static final String TARIFFS = "redirect:/tariffs/";
    @Autowired
    private ClientService clientService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private Logger logger;

    /**
     * Setting tariffs for new contract
     * @param model table add - to choose tariff
     * @return
     */
    @GetMapping("tariffs")
    public String start(Model model) {
        logger.info("Starting new contract");
        ContractDto contract = new ContractDto();
        model.addAttribute(contract);
        model.addAttribute(TABLE, "add");
        return "tariffs";
    }

    /**
     * Setting options for contract
     * @param model NEB for client buttons. Options (parents), option groups
     *              and children to group them in cards
     * @param contract
     * @param id
     * @return
     */
    @PostMapping("options")
    public String newContractTariffAdd(Model model, ContractDto contract, @RequestParam(name = "tariffID2") Integer id) {
        logger.info("Setting tariff in new contract, tariff id " + id);
        if (!contractService.setTariff(contract, id)) {
            model.addAttribute(MESSAGE, "Tariff is not valid");
            return ERROR;
        }
        model.addAttribute("options", contractService.getOptionsParents(contract));
        model.addAttribute("optionGroups", contractService.getGroups(contract));
        model.addAttribute("children", contractService.getOptionsChildren(contract));
        model.addAttribute(TABLE, "add");
        model.addAttribute("NEB", "yes");
        return "contractOptions";
    }

    /**
     * Form to set a client
     * @param model
     * @param contract
     * @param action existing or new for existing or new client accordingly
     * @param id
     * @return
     */
    @PostMapping("client")
    public String newContractOptionAdd(Model model, ContractDto contract, @RequestParam(name = "action") String action, @RequestParam(name = "optionID", required = false) List<Integer> id) {
        logger.info("Setting options and getting client for new contract, client " + action);
        if (!contractService.setOptions(contract, id, false)) {
            model.addAttribute(MESSAGE, "Options are not compatible");
            return ERROR;
        }
        model.addAttribute(TABLE, "add");
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

    /**
     * Setting existing client and redirecting to confirm page
     * @param model
     * @param contract
     * @param id
     * @param phoneNumber
     * @return
     */
    @PostMapping("/confirmExisting")
    public String newContractOptionAdd(Model model, ContractDto contract, @RequestParam(name = "clientID2") Integer id, @RequestParam String phoneNumber) {
        logger.info("Existing client, adding phone number" + phoneNumber);
        model.addAttribute(TABLE, "add");
        contract.setClient(clientService.getClient(id));
        contract.setPhoneNumber(phoneNumber);
        return "contract";
    }

    /**
     * Setting new client and redirecting to confirm page
     * @param model
     * @param contract
     * @param client
     * @param bindingResult
     * @return
     */
    @PostMapping("/confirm")
    public String newContractOptionAdd(Model model, ContractDto contract, @ModelAttribute @Valid ClientDto client, BindingResult bindingResult) {
        logger.info("New client, adding phone number" + client.getPhoneNumber());
        if (bindingResult.hasErrors()) {
            model.addAttribute(MESSAGE, "Operation cannot be proceeded further because received data contains some errors");
            return ERROR;
        }
        model.addAttribute(TABLE, "add");
        contract.setClient(client);
        contract.setPhoneNumber(client.getPhoneNumber());
        return "contract";
    }

    /**
     * Sending mail and adding contract
     * @param model
     * @param contract
     * @param email email with invoices sent to client if true
     * @param status
     * @return redirect to error page if email was not sent
     */
    @PostMapping("/confirm/true")
    public String confirmation(Model model, ContractDto contract, @RequestParam(required = false) Boolean email, SessionStatus status) {
        contractService.add(contract);
        if (email!=null) {
            boolean correct = contractService.sendEmail(true, contract);
            if (!correct) {
                model.addAttribute(MESSAGE, "Email was not sent");
                return ERROR;
            }
        }
        status.setComplete();
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

    /**
     * Sending mail and adding contract
     * @param model
     * @param contract
     * @param email email with invoices sent to client if true
     * @param status
     * @return redirect to error page if email was not sent
     */
    @PostMapping("/confirmExisting/true")
    public String confirmationExisting(Model model, ContractDto contract, @RequestParam(required = false) Boolean email, SessionStatus status) {
        contractService.add(contract);
        if (email!=null) {
            boolean correct = contractService.sendEmail(false, contract);
            if (!correct) {
                model.addAttribute(MESSAGE, "Email was not sent");
                return ERROR ;
            }
        }
        status.setComplete();
        return "redirect:/existingContract/" + contract.getPhoneNumber();
    }

    /**
     * Forbids backward movment in process
     * @param status
     * @return
     */
    @GetMapping(value={"options", "client", "confirm", "confirmExisting",
            "confirm/true", "confirmExisting/true"})
    public String tariffsBack(SessionStatus status) {
        status.setComplete();
        return TARIFFS;
    }
}
