package com.openclassrooms.paymybuddy.model;

import javax.persistence.*;

/**
 * The type Bank account.
 */
@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @Column(name = "iban")
    private String iban;
    @Column(name = "bank_establishment")
    private String bankEstablishment;
    @Column(name = "bic")
    private String bic;
    @Column(name = "balance")
    private double balance;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User accountOwner;


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
