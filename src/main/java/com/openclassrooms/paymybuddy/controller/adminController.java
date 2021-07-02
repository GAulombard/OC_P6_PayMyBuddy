package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Controller
public class adminController {

    private final Logger LOGGER = LoggerFactory.getLogger(adminController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/admin/users")
    @Transactional
    public ModelAndView getAllUsers() {
        LOGGER.info("HTTP GET request received at /admin/users");
        Map<String, Object> model = new HashMap<String,Object>();
        Iterable<User> result = userService.getUsers();

        model.put("users",result);

        return new ModelAndView("adminDashboard_Users",model);
    }

    @GetMapping("/admin/accounts")
    @Transactional
    public ModelAndView getAllBankAccounts() {
        LOGGER.info("HTTP GET request received at /admin/accounts");
        Map<String, Object> model = new HashMap<String,Object>();
        Iterable<BankAccount> result = bankAccountService.getAccounts();

        model.put("accounts",result);

        return new ModelAndView("adminDashboard_Accounts",model);
    }

    @GetMapping("/admin/transactions")
    public ModelAndView getAllTransactions() {
        LOGGER.info("HTTP GET request received at /admin/transactions");
        Map<String, Object> model = new HashMap<String,Object>();
        Iterable<Transaction> result = transactionService.getTransactions();

        model.put("transactions",result);

        return new ModelAndView("adminDashboard_Transactions",model);
    }
}
