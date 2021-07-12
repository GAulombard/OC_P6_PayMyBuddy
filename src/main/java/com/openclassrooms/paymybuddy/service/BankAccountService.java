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

@Service
public class BankAccountService {
    private final Logger LOGGER = LoggerFactory.getLogger(BankAccountService.class);

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ContactService contactService;

    public Iterable<BankAccount> getAccounts() {
        LOGGER.info("Processing to get all accounts");

        return bankAccountRepository.findAll();
    }

    public void saveBankAccount(BankAccount bankAccount) throws BankAccountAlreadyExistException {
        LOGGER.info("Processing to save a new bank account in database");

        if(bankAccountRepository.existsById(bankAccount.getIban())) {
            throw new BankAccountAlreadyExistException("Bank account already exist in database");
        }

        bankAccount.setBalance(BankAccountUtil.getRandomValueBetween(-5000,5000));//Simulate the balance between -500 and 5000
        bankAccount.setBankEstablishment(BankAccountUtil.getRandomBankNameFromEnum());

        bankAccountRepository.save(bankAccount);
    }

    public void removeBankAccountById(String id) {
        LOGGER.info("Processing to delete bank account");

        bankAccountRepository.deleteById(id);
    }

    public List<BankAccount> findAllBankAccountByOwnerId(int id) {
        List<BankAccount> result = new ArrayList<>();
        result = bankAccountRepository.findAllBankAccountByOwnerId(id);
        return result;
    }

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

}
