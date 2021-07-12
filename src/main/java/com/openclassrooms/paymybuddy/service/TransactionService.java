package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.MyUserDetails;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * The type Transaction service.
 */
@Service
public class TransactionService {

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    /**
     * The Bank account service.
     */
    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Gets transactions.
     *
     * @return the transactions
     */
    public Iterable<Transaction> getTransactions() {
        LOGGER.info("Process to get all transactions");
        return transactionRepository.findAll();
    }

    /**
     * Find all transactions by bank account iban list.
     *
     * @param iban the iban
     * @return the list
     */
    public List<Transaction> findAllTransactionsByBankAccountIban(String iban) {
        List<Transaction> result = transactionRepository.findAllTransactionsByBankAccountIban(iban);

        return result;
    }

    /**
     * Find all transactions by user id list.
     *
     * @param id the id
     * @return the list
     */
    public List<Transaction> findAllTransactionsByUserId(int id) {
        Iterable<BankAccount> accounts = bankAccountService.findAllBankAccountByOwnerId(id);
        List<String> ibans = new ArrayList<>();

        accounts.iterator().forEachRemaining(account -> {
            ibans.add(account.getIban());
        });

        List<Transaction> transactions = new ArrayList<>();

        ibans.iterator().forEachRemaining(iban -> {
            transactions.addAll(findAllTransactionsByBankAccountIban(iban));
        });

        return transactions;
    }

    /**
     * Save transaction.
     *
     * @param transaction the transaction
     */
    public void saveTransaction(Transaction transaction) {


        transaction.setDate(LocalDateTime.now());
        transactionRepository.save(transaction);

    }
}
