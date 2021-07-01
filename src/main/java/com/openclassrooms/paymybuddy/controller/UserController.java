package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.PayMyBuddyApplication;
import com.openclassrooms.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;


}
