package com.telekom.controller;

import com.telekom.model.entity.User;
import com.telekom.service.api.ClientService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AuthController {
    @Autowired
    ClientService clientService;

    @Autowired
    private Logger logger;

    @GetMapping("/login/{phone}")
    public String login(Model model, @PathVariable(value = "phone", required=false) String phone) {
        model.addAttribute("login", phone);
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/welcome")
    public String defaultAfterLogin(HttpServletRequest request, @AuthenticationPrincipal User activeUser) {
        Long id = activeUser.getId();
        if (request.isUserInRole("ADMIN")) {
            logger.info("Admin" +id+" is logged");
            return "redirect:/tariffs";
        }
        request.getSession().setAttribute("client", clientService.getOne(id));
        logger.info("Client" +id+" is logged");
        return "welcome";
    }


}
