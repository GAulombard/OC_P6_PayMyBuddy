package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The interface Bank account repository.
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

    /**
     * Find all bank account by owner id list.
     *
     * @param id the id
     * @return the list
     */
    @Query("SELECT b FROM BankAccount b WHERE b.accountOwner.userID = :id")
    List<BankAccount> findAllBankAccountByOwnerId(int id);

    /**
     * Update balance by iban.
     *
     * @param iban   the iban
     * @param amount the amount
     */
    @Modifying
    @Query("UPDATE BankAccount b SET b.balance = :amount WHERE b.iban = :iban")
    public void updateBalanceByIban(String iban, double amount);
}
