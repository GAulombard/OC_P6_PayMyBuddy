package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    @Query("SELECT t FROM Transaction t WHERE t.creditor.iban =: iban OR t.debtor.iban = :iban")
    List<Transaction> findAllTransactionsByBankAccountIban(String iban);
}
