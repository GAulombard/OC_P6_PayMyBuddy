package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> getTransactions() {
        LOGGER.info("Process to get all transactions");
        return transactionRepository.findAll();
    }
}
