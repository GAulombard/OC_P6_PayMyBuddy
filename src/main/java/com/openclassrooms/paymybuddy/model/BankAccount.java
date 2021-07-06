package com.openclassrooms.paymybuddy.model;

import javax.persistence.*;

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

    public User getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(User accountOwner) {
        this.accountOwner = accountOwner;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBankEstablishment() {
        return bankEstablishment;
    }

    public void setBankEstablishment(String bankEstablishment) {
        this.bankEstablishment = bankEstablishment;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
