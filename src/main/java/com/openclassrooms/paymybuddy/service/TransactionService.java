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

@Service
public class TransactionService {

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> getTransactions() {
        LOGGER.info("Process to get all transactions");
        return transactionRepository.findAll();
    }

    public List<Transaction> findAllTransactionsByBankAccountIban(String iban) {
        List<Transaction> result = transactionRepository.findAllTransactionsByBankAccountIban(iban);

        return result;
    }

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

    public void saveTransaction(Transaction transaction) {

        transaction.setDate(LocalDateTime.now());

        transactionRepository.save(transaction);

    }
}
