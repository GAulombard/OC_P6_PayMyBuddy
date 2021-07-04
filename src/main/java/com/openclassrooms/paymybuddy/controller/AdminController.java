package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.*;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.FeeService;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FeeService feeService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    @Transactional
    public ModelAndView getFullDashboard() {
        LOGGER.info("HTTP GET request received at /admin");
        Map<String, Object> model = new HashMap<String,Object>();
        Iterable<User> users = userService.getUsers();
        Iterable<BankAccount> accounts = bankAccountService.getAccounts();
        Iterable<Transaction> transactions = transactionService.getTransactions();

        model.put("users",users);
        model.put("accounts",accounts);
        model.put("transaction",transactions);

        return new ModelAndView("admin/adminFullDashboard",model);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    @Transactional
    public String getAllUsers(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /admin/users by: "+user.getEmail());
        Iterable<User> result = userService.getUsers();

        model.addAttribute("users",result);

        return "admin/adminDashboard_Users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/accounts")
    @Transactional
    public ModelAndView getAllBankAccounts() {
        LOGGER.info("HTTP GET request received at /admin/accounts");
        Map<String, Object> model = new HashMap<String,Object>();
        Iterable<BankAccount> result = bankAccountService.getAccounts();

        model.put("accounts",result);

        return new ModelAndView("admin/adminDashboard_Accounts",model);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/transactions")
    public ModelAndView getAllTransactions() {
        LOGGER.info("HTTP GET request received at /admin/transactions");
        Map<String, Object> model = new HashMap<String,Object>();
        Iterable<Transaction> result = transactionService.getTransactions();

        model.put("transactions",result);

        return new ModelAndView("admin/adminDashboard_Transactions",model);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/fees")
    @Transactional
    public String getAllFees(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /admin/fees by: "+user.getEmail());
        Iterable<Fee> result = feeService.getFees();

        model.addAttribute("fees",result);

        return "admin/adminDashboard_Fees";
    }
}
