package com.openclassrooms.paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @GetMapping("/login")
    public ModelAndView getLoginPage(){
        LOGGER.info("HTTP GET request received at /login");
        Map<String, Object> model = new HashMap<String,Object>();
        return new ModelAndView("login",model);
    }
}
