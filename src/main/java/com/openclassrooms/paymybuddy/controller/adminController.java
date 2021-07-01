package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.PayMyBuddyApplication;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class adminController {

    private final Logger LOGGER = LoggerFactory.getLogger(adminController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public ModelAndView getAllUsers() {
        LOGGER.info("HTTP GET request received at /admin/users");
        Map<String, Object> model = new HashMap<String,Object>();
        Iterable<User> result = userService.getUsers();

        model.put("users",result);

        return new ModelAndView("adminDashboard",model);
    }
}
