package com.telekom.controller;

import com.telekom.entity.User;
import com.telekom.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

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
