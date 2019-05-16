package com.telekom.controller;


import com.telekom.model.entity.User;
import com.telekom.service.api.ClientService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@Controller
public class AuthController {
    String login="login";
    @Autowired
    ClientService clientService;

    @Autowired
    private Logger logger;

    @GetMapping("/login/{phone}")
    public String login(Model model, @PathVariable(value = "phone", required=false) String phone) {
        model.addAttribute(login, phone);
        return login;
    }

    @GetMapping("/login")
    public String login() {
        return login;
    }

    @GetMapping("/welcome")
    public String defaultAfterLogin(HttpServletRequest request, Principal principal) {

        User activeUser = (User)((Authentication) principal).getPrincipal();
        Long id=activeUser.getId();
        if (request.isUserInRole("ADMIN")) {
            logger.info("Admin" +id+" is logged");
            return "redirect:/tariffs";
        }
        request.getSession().setAttribute("client", clientService.getClient(id));
        logger.info("Client" +id+" is logged");
        return "welcome";
    }


}
