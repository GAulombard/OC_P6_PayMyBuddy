package com.openclassrooms.paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Login controller.
 */
@Controller
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * Get login page string.
     *
     * @param model the model
     * @return the string
     */
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String getLoginPage(Model model){
        LOGGER.info("HTTP GET request received at /login");

        return "login";
    }
}
