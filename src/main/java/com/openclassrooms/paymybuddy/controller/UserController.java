package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.PayMyBuddyApplication;
import com.openclassrooms.paymybuddy.model.MyUserDetails;
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

    @RolesAllowed({"USER","ADMIN"})
    @GetMapping("/user/home")
    public String getHome(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /user/home by: "+user.getEmail());
        return "user/home";
    }

    @RolesAllowed({"USER","ADMIN"})
    @GetMapping("/user/contact")
    public String getContact(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /user/contact by: "+user.getEmail());
        return "user/contact";
    }

    @RolesAllowed({"USER","ADMIN"})
    @GetMapping("/user/profile")
    public String getProfile(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /user/profile by: "+user.getEmail());
        model.addAttribute("user",user);
        return "user/profile";
    }

    @RolesAllowed({"USER","ADMIN"})
    @GetMapping("/user/transfer")
    public String getTransfer(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /user/transfer by: "+user.getEmail());
        return "user/transfer";
    }

}
