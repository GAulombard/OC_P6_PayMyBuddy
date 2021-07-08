package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.PayMyBuddyApplication;
import com.openclassrooms.paymybuddy.exception.BankAccountAlreadyExistException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.MyUserDetails;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.util.Optional;

@Controller
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @RolesAllowed({"USER","ADMIN"})
    @GetMapping("/user/home")
    public String getHome(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /user/home by: "+user.getEmail());

        model.addAttribute("total",userService.getTotalAccountBalanceByUserId(user.getUserID()));
        model.addAttribute("accounts",user.getAccountList());

        return "user/home";
    }

    @RolesAllowed({"USER","ADMIN"})
    @GetMapping("/user/contact")
    @Transactional
    public String getContact(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /user/contact by: "+user.getEmail());

        //model.addAttribute("contacts",(user.getContactList()));
        model.addAttribute("contacts",(userService.getUserById(user.getUserID())).getContactList());

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

    @RolesAllowed({"USER","ADMIN"})
    @GetMapping("/user/deletemypmb")
    @Transactional
    public String deleteUserAccount(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /user/deletemypmb by: "+user.getEmail());

        userService.removeUserById(user.getUserID());

        return "redirect:/logout";
    }

    @RolesAllowed({"USER","ADMIN"})
    @GetMapping("/user/accountform")
    public String getAccountForm(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /user/accountform by: "+user.getEmail());

        model.addAttribute("account",new BankAccount());

        return "user/accountForm";
    }

    @RolesAllowed({"USER","ADMIN"})
    @PostMapping("/user/createaccount") //Bank Account
    @Transactional
    public String saveBankAccount(@ModelAttribute("account") BankAccount account, @AuthenticationPrincipal MyUserDetails user, Model model) throws BankAccountAlreadyExistException {
        LOGGER.info("HTTP POST request received at /user/accountform by: "+user.getEmail());

        account.setAccountOwner(userService.getUserById(user.getUserID()));
        bankAccountService.saveBankAccount(account);

        return "redirect:/user/home";
    }

    @RolesAllowed({"USER","ADMIN"})
    @GetMapping("/user/deleteaccount{id}") //Bank Account
    @Transactional
    public String deleteBankAccount(@RequestParam("id") String id, @AuthenticationPrincipal MyUserDetails user) {
        LOGGER.info("HTTP GET request received at /user/deleteaccount{id} by: "+user.getEmail());

        bankAccountService.removeBankAccountById(id);

        return "redirect:/user/home";
    }
}
