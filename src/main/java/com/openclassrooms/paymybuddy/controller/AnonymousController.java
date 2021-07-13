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

import javax.validation.Valid;

/**
 * The type Anonymous controller.
 */
@Controller
public class AnonymousController {

    private final Logger LOGGER = LoggerFactory.getLogger(AnonymousController.class);

    @Autowired
    private UserService userService;

    /**
     * Gets index.
     *
     * @return the index
     */
    @GetMapping({"/index"})
    public String getIndex() {
        LOGGER.info("HTTP GET request received at /index");
        return "index";
    }


    /**
     * Gets signup form.
     *
     * @param model the model
     * @return the signup form
     */
    @GetMapping("/signup")
    public String getSignupForm(Model model) {
        LOGGER.info("HTTP GET request received at /signup");
        model.addAttribute("user",new User());

        return "signup";
    }


    /**
     * Add new user string.
     *
     * @param user  the user
     * @param model the model
     * @return the string
     * @throws UserAlreadyExistException the user already exist exception
     */
    @PostMapping("/signup/save")
    public String addNewUser(@Valid @ModelAttribute("user") User user, Model model, BindingResult bindingResult) throws UserAlreadyExistException {
        LOGGER.info("HTTP POST request received at /signup/save");

        if(bindingResult.hasErrors()){
            return "redirect:/signup";
        }

        userService.saveUser(user);

        return "redirect:/login";
    }

}
