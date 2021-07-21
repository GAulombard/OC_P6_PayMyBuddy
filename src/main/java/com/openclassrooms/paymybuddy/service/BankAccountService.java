package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.BankAccountAlreadyExistException;
import com.openclassrooms.paymybuddy.exception.BankAccountNotFoundException;
import com.openclassrooms.paymybuddy.exception.UserAlreadyExistException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.Contact;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.util.BankAccountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The type Bank account service.
 */
@Service
public class BankAccountService {
    private final Logger LOGGER = LoggerFactory.getLogger(BankAccountService.class);

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ContactService contactService;

    /**
     * Gets accounts.
     *
     * @return the accounts
     */
    public Iterable<BankAccount> getAccounts() {
        LOGGER.info("Processing to get all accounts");

        return bankAccountRepository.findAll();
    }

    /**
     * Save bank account.
     *
     * @param bankAccount the bank account
     * @throws BankAccountAlreadyExistException the bank account already exist exception
     */
    public void saveBankAccount(BankAccount bankAccount) throws BankAccountAlreadyExistException {
        LOGGER.info("Processing to save a new bank account in database");

        if (bankAccountRepository.existsById(bankAccount.getIban())) {
            throw new BankAccountAlreadyExistException("Bank account already exists in database");
        }

        bankAccount.setIban(bankAccount.getIban().toUpperCase(Locale.ROOT));
        bankAccount.setBic(bankAccount.getBic().toUpperCase(Locale.ROOT));
        bankAccount.setBalance(BankAccountUtil.getRandomValueBetween(-5000, 5000));//Simulate the balance between -500 and 5000
        bankAccount.setBankEstablishment(BankAccountUtil.getRandomBankNameFromEnum());

        bankAccountRepository.save(bankAccount);
    }

    /**
     * Remove bank account by id.
     *
     * @param id the id
     * @throws BankAccountNotFoundException the bank account not found exception
     */
    public void removeBankAccountById(String id) throws BankAccountNotFoundException {
        LOGGER.info("Processing to delete bank account");

        if (bankAccountRepository.existsById(id)) {
            bankAccountRepository.deleteById(id);
        } else {
            throw new BankAccountNotFoundException("Bank Account not found");
        }

    }

    /**
     * Find all bank account by owner id list.
     *
     * @param id the id
     * @return the list
     * @throws BankAccountNotFoundException the bank account not found exception
     */
    public List<BankAccount> findAllBankAccountByOwnerId(int id) throws BankAccountNotFoundException {
        LOGGER.info("Processing to get all bank account by owner Id");

        List<BankAccount> result = new ArrayList<>();
        result = bankAccountRepository.findAllBankAccountByOwnerId(id);

        if(result == null) throw new BankAccountNotFoundException("No bank account found");

        return result;
    }

    /**
     * Gets all contact bank account by id.
     *
     * @param id the id
     * @return the all contact bank account by id
     */
    public List<BankAccount> getAllContactBankAccountById(int id) {
        LOGGER.info("Processing to get all contact bank account by Id");

        List<BankAccount> result = new ArrayList<>();
        List<User> contacts = contactService.getAllMyContactById(id);
        List<BankAccount> allAccounts = bankAccountRepository.findAll();

        contacts.iterator().forEachRemaining(contact -> {
            allAccounts.iterator().forEachRemaining(account -> {
                if (account.getAccountOwner().getUserID() == contact.getUserID()) {
                    result.add(account);
                }
            });
        });

        return result;
    }

    /**
     * Update balance by iban.
     *
     * @param iban   the iban
     * @param amount the amount
     * @throws BankAccountNotFoundException the bank account not found exception
     */
    public void updateBalanceByIban(String iban, double amount) throws BankAccountNotFoundException {
        LOGGER.info("Processing to update balance by iban");

        if (bankAccountRepository.existsById(iban)) {
            bankAccountRepository.updateBalanceByIban(iban, amount);
        }else {
            throw new BankAccountNotFoundException("Bank account not found");
        }



    }

}
