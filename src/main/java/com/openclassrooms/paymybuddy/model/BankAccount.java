package com.openclassrooms.paymybuddy.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * The type Bank account.
 */
@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @Column(name = "iban")
    @NotEmpty(message = "Iban is mandatory")
    @Size(min = 27,max = 27,message = "Iban contains 27 characters")
    private String iban;
    @Column(name = "bank_establishment")
    private String bankEstablishment;
    @Column(name = "bic")
    @NotEmpty(message = "Bic is mandatory")
    @Size(min = 11,max = 11,message = "Bic contains 11 characters")
    private String bic;
    @Column(name = "balance")
    private double balance;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User accountOwner;

    /*@OneToMany
    @JoinColumn(name = "reference")
    private List<Transaction> transactionList;

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }*/

    /**
     * Gets account owner.
     *
     * @return the account owner
     */
    public User getAccountOwner() {
        return accountOwner;
    }

    /**
     * Sets account owner.
     *
     * @param accountOwner the account owner
     */
    public void setAccountOwner(User accountOwner) {
        this.accountOwner = accountOwner;
    }

    /**
     * Gets iban.
     *
     * @return the iban
     */
    public String getIban() {
        return iban;
    }

    /**
     * Sets iban.
     *
     * @param iban the iban
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Gets bank establishment.
     *
     * @return the bank establishment
     */
    public String getBankEstablishment() {
        return bankEstablishment;
    }

    /**
     * Sets bank establishment.
     *
     * @param bankEstablishment the bank establishment
     */
    public void setBankEstablishment(String bankEstablishment) {
        this.bankEstablishment = bankEstablishment;
    }

    /**
     * Gets bic.
     *
     * @return the bic
     */
    public String getBic() {
        return bic;
    }

    /**
     * Sets bic.
     *
     * @param bic the bic
     */
    public void setBic(String bic) {
        this.bic = bic;
    }

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
