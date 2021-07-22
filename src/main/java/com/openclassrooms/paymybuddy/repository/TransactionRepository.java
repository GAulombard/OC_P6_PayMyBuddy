package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Transaction repository.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    /**
     * Find all transactions by bank account iban list.
     *
     * @param iban the iban
     * @return the list
     */
    @Query("SELECT t FROM Transaction t WHERE t.creditor.iban =: iban OR t.debtor.iban = :iban ORDER BY t.date DESC")
    List<Transaction> findAllTransactionsByBankAccountIban(String iban);
}
