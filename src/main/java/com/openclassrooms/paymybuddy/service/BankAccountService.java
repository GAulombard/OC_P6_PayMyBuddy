package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.BankAccountAlreadyExistException;
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

        if(bankAccountRepository.existsById(bankAccount.getIban())) {
            throw new BankAccountAlreadyExistException("Bank account already exist in database");
        }

        bankAccount.setBalance(BankAccountUtil.getRandomValueBetween(-5000,5000));//Simulate the balance between -500 and 5000
        bankAccount.setBankEstablishment(BankAccountUtil.getRandomBankNameFromEnum());

        bankAccountRepository.save(bankAccount);
    }

    /**
     * Remove bank account by id.
     *
     * @param id the id
     */
    public void removeBankAccountById(String id) {
        LOGGER.info("Processing to delete bank account");

        bankAccountRepository.deleteById(id);
    }

    /**
     * Find all bank account by owner id list.
     *
     * @param id the id
     * @return the list
     */
    public List<BankAccount> findAllBankAccountByOwnerId(int id) {
        List<BankAccount> result = new ArrayList<>();
        result = bankAccountRepository.findAllBankAccountByOwnerId(id);
        return result;
    }

    /**
     * Gets all contact bank account by id.
     *
     * @param id the id
     * @return the all contact bank account by id
     */
    public List<BankAccount> getAllContactBankAccountById(int id) {
        List<BankAccount> result = new ArrayList<>();
        List<User> contacts = contactService.getAllMyContactById(id);
        List<BankAccount> allAccounts = bankAccountRepository.findAll();

        contacts.iterator().forEachRemaining(contact -> {
            allAccounts.iterator().forEachRemaining(account ->{
                if(account.getAccountOwner().getUserID() == contact.getUserID()) {
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
     */
    public void updateBalanceByIban(String iban, double amount) {

        bankAccountRepository.updateBalanceByIban(iban,amount);

    }

}
