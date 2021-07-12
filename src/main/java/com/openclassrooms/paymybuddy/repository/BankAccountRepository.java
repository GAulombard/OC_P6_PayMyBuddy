package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

    @Query("SELECT b FROM BankAccount b WHERE b.accountOwner.userID = :id")
    List<BankAccount> findAllBankAccountByOwnerId(int id);

    @Modifying
    @Query("UPDATE BankAccount b SET b.balance = :amount WHERE b.iban = :iban")
    public void updateBalanceByIban(String iban, double amount);
}
