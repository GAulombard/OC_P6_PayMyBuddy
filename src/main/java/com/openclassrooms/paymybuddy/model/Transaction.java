package com.openclassrooms.paymybuddy.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reference")
    private int reference;

    @Column(name = "amount")
    private double amount;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private LocalDate date;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "debtor")
    private BankAccount debtor;

    @OneToOne
    @JoinColumn(name = "creditor")
    private BankAccount creditor;

    public BankAccount getDebtor() {
        return debtor;
    }

    public void setDebtor(BankAccount debtor) {
        this.debtor = debtor;
    }

    public BankAccount getCreditor() {
        return creditor;
    }

    public void setCreditor(BankAccount creditor) {
        this.creditor = creditor;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
