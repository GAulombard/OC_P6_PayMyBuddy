package com.openclassrooms.paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnonymousController {

    private final Logger LOGGER = LoggerFactory.getLogger(AnonymousController.class);

    @GetMapping({"/","/index"})
    public String getIndex() {
        LOGGER.info("HTTP GET request received at /index");
        return "index";
    }

    @GetMapping("/signup")
    public String getSignupForm() {
        LOGGER.info("HTTP GET request received at /signup");
        return "signup";
    }

}
