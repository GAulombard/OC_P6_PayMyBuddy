package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.BankAccountNotFoundException;
import com.openclassrooms.paymybuddy.model.*;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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
        return transactionRepository.findAll(Sort.by(Sort.Direction.DESC,"date"));
    }

    /**
     * Find all transactions by bank account iban list.
     *
     * @param iban the iban
     * @return the list
     */
    public List<Transaction> findAllTransactionsByBankAccountIban(String iban) {
        LOGGER.info("Process to get all transactions by bank account iban");

        List<Transaction> result = transactionRepository.findAllTransactionsByBankAccountIban(iban);

        return result;
    }

    /**
     * Find all transactions by user id list.
     *
     * @param id the id
     * @return the list
     * @throws BankAccountNotFoundException the bank account not found exception
     */
    public List<Transaction> findAllTransactionsByUserId(int id) throws BankAccountNotFoundException {
        LOGGER.info("Process to find all transactions by user Id");

        Iterable<BankAccount> accounts = bankAccountService.findAllBankAccountByOwnerId(id);
        List<String> ibans = new ArrayList<>();

        accounts.iterator().forEachRemaining(account -> {
            ibans.add(account.getIban());
        });

        List<Transaction> transactions = new ArrayList<>();

        ibans.iterator().forEachRemaining(iban -> {
            transactions.addAll(findAllTransactionsByBankAccountIban(iban));
        });

        Set setItems = new LinkedHashSet(transactions);
        transactions.clear();
        transactions.addAll(setItems);

        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return transactions;
    }

    /**
     * Save transaction.
     *
     * @param transaction the transaction
     */
    public void saveTransaction(Transaction transaction) {
        LOGGER.info("Process to save new transaction");

            transaction.setDate(LocalDateTime.now());
            transactionRepository.save(transaction);

    }

    public double getTotalTransactionBalance() {
        AtomicReference<Double> result = new AtomicReference<>((double) 0);

        List<Transaction> transactions = transactionRepository.findAll();

        if (transactions == null) return result.get();

        transactions.iterator().forEachRemaining(transaction -> {
            result.updateAndGet(v -> new Double((double) (v + transaction.getAmount())));
        });

        return Precision.round(result.get(),2);
    }
}
