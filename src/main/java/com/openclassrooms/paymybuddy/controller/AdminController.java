package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.constants.Constants;
import com.openclassrooms.paymybuddy.exception.BankAccountNotFoundException;
import com.openclassrooms.paymybuddy.exception.UserNotFoundException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Admin controller.
 */
@Controller
public class AdminController implements WebMvcConfigurer {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FeeService feeService;

    /**
     * Gets full dashboard.
     *
     * @param user  the user
     * @param model the model
     * @return the full dashboard
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    @Transactional
    public String getFullDashboard(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /admin by: "+user.getEmail());

        Iterable<User> users = userService.getUsers();
        Iterable<BankAccount> accounts = bankAccountService.getAccounts();
        Iterable<Transaction> transactions = transactionService.getTransactions();
        Iterable<Fee> fees = feeService.getFees();

        model.addAttribute("users",users);
        model.addAttribute("accounts",accounts);
        model.addAttribute("transactions",transactions);
        model.addAttribute("fees",fees);

        return "admin/adminFullDashboard";
    }

    /**
     * Gets all users.
     *
     * @param user  the user
     * @param model the model
     * @return the all users
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    @Transactional
    public String getAllUsers(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /admin/users by: "+user.getEmail());

        Iterable<User> result = userService.getUsers();

        model.addAttribute("users",result);

        return "admin/adminDashboard_Users";
    }

    /**
     * Gets all bank accounts.
     *
     * @param user  the user
     * @param model the model
     * @return the all bank accounts
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/accounts")
    @Transactional
    public String getAllBankAccounts(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /admin/accounts by: "+user.getEmail());

        Iterable<BankAccount> result = bankAccountService.getAccounts();

        model.addAttribute("accounts",result);

        return "admin/adminDashboard_Accounts";
    }

    /**
     * Gets all transactions.
     *
     * @param user  the user
     * @param model the model
     * @return the all transactions
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/transactions")
    public String getAllTransactions(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /admin/transactionsby: "+user.getEmail());

        Iterable<Transaction> result = transactionService.getTransactions();

        model.addAttribute("transactions",result);

        return "admin/adminDashboard_Transactions";
    }

    /**
     * Gets all fees.
     *
     * @param user  the user
     * @param model the model
     * @return the all fees
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/fees")
    @Transactional
    public String getAllFees(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /admin/fees by: "+user.getEmail());
        Iterable<Fee> result = feeService.getFees();

        model.addAttribute("fees",result);

        return "admin/adminDashboard_Fees";
    }

    /**
     * Delete user string.
     *
     * @param user the user
     * @param id   the id
     * @return the string
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/delete")
    @Transactional
    public String deleteUser(@AuthenticationPrincipal MyUserDetails user, @RequestParam("id") int id) throws UserNotFoundException {
        LOGGER.info("HTTP GET request received at /admin/delete?id={id} by: "+user.getEmail());

        userService.removeUserById(id);


        return "redirect:/admin/users";
    }

    /**
     * Make admin string.
     *
     * @param user the user
     * @param id   the id
     * @return the string
     * @throws UserNotFoundException the user not found exception
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/make-admin")
    @Transactional
    public String makeAdmin(@AuthenticationPrincipal MyUserDetails user, @RequestParam("id") int id) throws UserNotFoundException {
        LOGGER.info("HTTP GET request received at /admin/make-admin?id={id} by: "+user.getEmail());

        userService.updateRoleById(id, Constants.ROLE_ADMIN);

        return "redirect:/admin/users";
    }

    /**
     * Make user string.
     *
     * @param user the user
     * @param id   the id
     * @return the string
     * @throws UserNotFoundException the user not found exception
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/make-user")
    @Transactional
    public String makeUser(@AuthenticationPrincipal MyUserDetails user, @RequestParam("id") int id) throws UserNotFoundException {
        LOGGER.info("HTTP GET request received at /admin/make-user?id={id} by: "+user.getEmail());

        userService.updateRoleById(id, Constants.ROLE_USER);

        return "redirect:/admin/users";
    }

    /**
     * Recover user string.
     *
     * @param user the user
     * @param id   the id
     * @return the string
     * @throws UserNotFoundException the user not found exception
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/user-recovery")
    @Transactional
    public String recoverUser(@AuthenticationPrincipal MyUserDetails user, @RequestParam("id") int id) throws UserNotFoundException {
        LOGGER.info("HTTP GET request received at /admin/user-recovery?id={id} by: "+user.getEmail());

        userService.updateDeletedById(id);

        return "redirect:/admin/users";
    }

    /**
     * Delete account string.
     *
     * @param user the user
     * @param id   the id
     * @return the string
     * @throws BankAccountNotFoundException the bank account not found exception
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/delete-account")
    @Transactional
    public String deleteAccount(@AuthenticationPrincipal MyUserDetails user, @RequestParam("id") String id) throws BankAccountNotFoundException {
        LOGGER.info("HTTP GET request received at /admin/delete-account?id={id} by: "+user.getEmail());

        bankAccountService.removeBankAccountById(id);


        return "redirect:/admin/accounts";
    }

    /**
     * Recover account string.
     *
     * @param user the user
     * @param id   the id
     * @return the string
     * @throws BankAccountNotFoundException the bank account not found exception
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/account-recovery")
    @Transactional
    public String recoverAccount(@AuthenticationPrincipal MyUserDetails user, @RequestParam("id") String id) throws BankAccountNotFoundException, UserNotFoundException {
        LOGGER.info("HTTP GET request received at /admin/account-recovery?id={id} by: "+user.getEmail());

        bankAccountService.updateDeletedById(id);

        return "redirect:/admin/accounts";
    }
}
