package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.PayMyBuddyApplication;
import com.openclassrooms.paymybuddy.constants.Constants;
import com.openclassrooms.paymybuddy.exception.*;
import com.openclassrooms.paymybuddy.model.*;
import com.openclassrooms.paymybuddy.service.*;
import com.openclassrooms.paymybuddy.util.BankAccountUtil;
import com.openclassrooms.paymybuddy.util.FeeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The type User controller.
 */
@Controller
public class UserController implements WebMvcConfigurer {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FeeService feeService;

    /**
     * Gets home.
     *
     * @param user  the user
     * @param model the model
     * @return the home
     */
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/user/home")
    public String getHome(@AuthenticationPrincipal MyUserDetails user, Model model) throws UserNotFoundException {
        LOGGER.info("HTTP GET request received at /user/home by: " + user.getEmail());

        model.addAttribute("total", userService.getTotalAccountBalanceByUserId(user.getUserID()));
        model.addAttribute("accounts", userService.getUserById(user.getUserID()).getAccountList());

        return "user/home";
    }

    /**
     * Gets contact.
     *
     * @param user  the user
     * @param model the model
     * @return the contact
     */
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/user/contact")
    @Transactional
    public String getContact(@AuthenticationPrincipal MyUserDetails user, Model model) throws UserNotFoundException {
        LOGGER.info("HTTP GET request received at /user/contact by: " + user.getEmail());

        model.addAttribute("users", userService.getUsers());
        model.addAttribute("contacts", (userService.getUserById(user.getUserID())).getContactList());
        model.addAttribute("contactsOf", (userService.getUserById(user.getUserID())).getContactListOf());

        return "user/contact";
    }

    /**
     * Gets profile.
     *
     * @param user  the user
     * @param model the model
     * @return the profile
     */
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/user/profile")
    public String getProfile(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /user/profile by: " + user.getEmail());
        model.addAttribute("user", user);
        return "user/profile";
    }

    /**
     * Gets transfer.
     *
     * @param user  the user
     * @param model the model
     * @return the transfer
     * @throws BankAccountNotFoundException the bank account not found exception
     */
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/user/transfer")
    @Transactional
    public String getTransfer(@AuthenticationPrincipal MyUserDetails user, Model model) throws BankAccountNotFoundException, UserNotFoundException {
        LOGGER.info("HTTP GET request received at /user/transfer by: " + user.getEmail());

        List<Transaction> transactions = transactionService.findAllTransactionsByUserId(user.getUserID());
        List<BankAccount> contactAccount = bankAccountService.getAllContactBankAccountById(user.getUserID());
        List<BankAccount> myAccounts = bankAccountService.findAllBankAccountByOwnerId(user.getUserID());
        List<BankAccount> creditorAccounts = new ArrayList<>();

        creditorAccounts.addAll(contactAccount);
        creditorAccounts.addAll(myAccounts);

        model.addAttribute("feeRate",Constants.RATE100);
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("contactAccount", contactAccount);
        model.addAttribute("myAccounts", myAccounts);
        model.addAttribute("creditorAccounts", creditorAccounts);
        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);
        return "user/transfer";
    }

    /**
     * Delete user account string.
     *
     * @param user  the user
     * @param model the model
     * @return the string
     */
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/user/deletemypmb")
    @Transactional
    public String deleteUserAccount(@AuthenticationPrincipal MyUserDetails user, Model model) throws UserNotFoundException {
        LOGGER.info("HTTP GET request received at /user/deletemypmb by: " + user.getEmail());

        userService.removeUserById(user.getUserID());

        return "redirect:/logout";
    }

    /**
     * Gets account form.
     *
     * @param user  the user
     * @param model the model
     * @return the account form
     */
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/user/accountform")
    public String getAccountForm(@AuthenticationPrincipal MyUserDetails user, Model model) {
        LOGGER.info("HTTP GET request received at /user/accountform by: " + user.getEmail());

        model.addAttribute("account", new BankAccount());

        return "user/accountForm";
    }

    /**
     * Save bank account string.
     *
     * @param account       the account
     * @param bindingResult the binding result
     * @param user          the user
     * @return the string
     * @throws BankAccountAlreadyExistException the bank account already exist exception
     */
    @RolesAllowed({"USER", "ADMIN"})
    @PostMapping("/user/createaccount") //Bank Account
    @Transactional
    public String saveBankAccount(@Valid @ModelAttribute("account") BankAccount account,BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails user) throws BankAccountAlreadyExistException, UserNotFoundException {
        LOGGER.info("HTTP POST request received at /user/createaccount by: " + user.getEmail());

        if (bindingResult.hasErrors()) {
            return "/user/accountForm";
        }

        account.setAccountOwner(userService.getUserById(user.getUserID()));
        bankAccountService.saveBankAccount(account);

        return "redirect:/user/home";
    }

    /**
     * Delete bank account string.
     *
     * @param id   the id
     * @param user the user
     * @return the string
     * @throws BankAccountNotFoundException the bank account not found exception
     */
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/user/deleteaccount{id}") //Bank Account
    @Transactional
    public String deleteBankAccount(@RequestParam("id") String id, @AuthenticationPrincipal MyUserDetails user) throws BankAccountNotFoundException {
        LOGGER.info("HTTP GET request received at /user/deleteaccount?id={id} by: " + user.getEmail());

        bankAccountService.removeBankAccountById(id);

        return "redirect:/user/home";
    }

    /**
     * Add contact string.
     *
     * @param user  the user
     * @param model the model
     * @param email the email
     * @return the string
     * @throws UserNotFoundException the user not found exception
     * @throws ContactException      the contact exception
     */
    @RolesAllowed({"USER", "ADMIN"})
    @PostMapping("/user/addcontact")
    @Transactional
    public String addContact(@AuthenticationPrincipal MyUserDetails user, Model model, @RequestParam String email) throws UserNotFoundException, ContactException {
        LOGGER.info("HTTP POST request received at /user/addcontact by: " + user.getEmail());

        contactService.saveContactRelationship(user, userService.findUserByEmail(email));

        return "redirect:/user/contact";
    }

    /**
     * Delete contact string.
     *
     * @param id   the id
     * @param user the user
     * @return the string
     * @throws UserNotFoundException the user not found exception
     */
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/user/delete-contact{id}") //Bank Account
    @Transactional
    public String deleteContact(@RequestParam("id") int id, @AuthenticationPrincipal MyUserDetails user) throws Exception {
        LOGGER.info("HTTP GET request received at /user/delete-contact{id} by: " + user.getEmail());

        contactService.deleteContactByUserIdAndContactUserId(user.getUserID(), id);

        return "redirect:/user/contact";
    }

    /**
     * Save transaction string.
     *
     * @param transaction   the transaction
     * @param user          the user
     * @param bindingResult the binding result
     * @return the string
     * @throws BankAccountNotFoundException the bank account not found exception
     * @throws InsufficientFoundException   the insufficient found exception
     */
    @RolesAllowed({"USER", "ADMIN"})
    @PostMapping("/user/new-transfer") //Bank Account
    @Transactional
    public String saveTransaction(@Valid @ModelAttribute("transaction") Transaction transaction,BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails user) throws BankAccountNotFoundException, InsufficientFoundException {
        LOGGER.info("HTTP POST request received at /user/new-transfer by: " + user.getEmail());

        if (bindingResult.hasErrors()) {
            return "/user/transfer";
        }

        if (transaction.getDebtor().equals(transaction.getCreditor())) {
            LOGGER.info("Bad request");
            return "/error/400";
        }

        if (BankAccountUtil.isSufficientFound(transaction.getDebtor().getBalance(), FeeUtil.feeCalculator(Constants.RATE100, transaction.getAmount()))) {
            transactionService.saveTransaction(transaction);

            Fee fee = new Fee();
            fee.setTransactionReference(transaction.getReference());
            fee.setAccount(transaction.getDebtor().getIban());
            fee.setAmount(FeeUtil.feeCalculator(Constants.RATE100, transaction.getAmount()));

            double debtorBalance = BankAccountUtil.balanceCalculator(transaction.getDebtor().getBalance(), fee.getAmount(), transaction.getAmount());
            double creditorBalance = BankAccountUtil.balanceCalculator(transaction.getCreditor().getBalance(), 0, -transaction.getAmount());

            feeService.saveFee(fee);
            bankAccountService.updateBalanceByIban(transaction.getDebtor().getIban(), debtorBalance);
            bankAccountService.updateBalanceByIban(transaction.getCreditor().getIban(), creditorBalance);

            return "redirect:/user/transfer";
        } else {
            throw new InsufficientFoundException("Insufficient found");
        }
    }

}
