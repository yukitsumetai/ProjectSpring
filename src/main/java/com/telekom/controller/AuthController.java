package com.telekom.controller;

import com.telekom.entity.User;
import com.telekom.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AuthController {
    @Autowired
    ClientService clientService;

    @GetMapping("/login")
    public String login() {
        return "login";

    }

    @GetMapping("/welcome")
    public String defaultAfterLogin( HttpServletRequest request, @AuthenticationPrincipal User activeUser) {
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/tariffs";
        }
        Long id=activeUser.getId();
       request.getSession().setAttribute("client",clientService.getOne(id));
        return "welcome";
    }


}
