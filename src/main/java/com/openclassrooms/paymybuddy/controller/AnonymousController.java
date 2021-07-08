package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.exception.UserAlreadyExistException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AnonymousController {

    private final Logger LOGGER = LoggerFactory.getLogger(AnonymousController.class);

    @Autowired
    private UserService userService;

    @GetMapping({"/index"})
    public String getIndex() {
        LOGGER.info("HTTP GET request received at /index");
        return "index";
    }


    @GetMapping("/signup")
    public String getSignupForm(Model model) {
        LOGGER.info("HTTP GET request received at /signup");
        model.addAttribute("user",new User());
        return "signup";
    }


    @PostMapping("/signup/save")
    public String addNewUser(@ModelAttribute("user") User user, Model model) throws UserAlreadyExistException {
        LOGGER.info("HTTP POST request received at /signup/save");

        userService.saveUser(user);

        return "redirect:/login";
    }

}
