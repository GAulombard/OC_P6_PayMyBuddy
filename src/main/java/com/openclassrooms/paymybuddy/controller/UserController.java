package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.PayMyBuddyApplication;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    //@PreAuthorize("hasRole('USER')")
    @RolesAllowed({"USER","ADMIN"})
    @GetMapping("/user/home")
    public String getHome(@AuthenticationPrincipal User user, Model model) {
        LOGGER.info("HTTP GET request received at /user/home by: ");
        return "user/home";
    }

}
