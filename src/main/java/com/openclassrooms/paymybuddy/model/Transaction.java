package com.openclassrooms.paymybuddy.model;

import org.apache.commons.math3.util.Precision;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Transaction.
 */
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reference")
    private int reference;

    @NotNull
    @Positive(message = "Amount should be positive")
    @Column(name = "amount")
    private double amount;

    @Size(max = 50,message = "Message should be maximum 50 characters")
    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private LocalDateTime date;

    @NotNull(message = "Debtor is mandatory")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "debtor")
    private BankAccount debtor;

    @NotNull(message = "Creditor is mandatory")
    @OneToOne
    @JoinColumn(name = "creditor")
    private BankAccount creditor;

    /**
     * Gets debtor.
     *
     * @return the debtor
     */
    public BankAccount getDebtor() {
        return debtor;
    }

    /**
     * Sets debtor.
     *
     * @param debtor the debtor
     */
    public void setDebtor(BankAccount debtor) {
        this.debtor = debtor;
    }

    /**
     * Gets creditor.
     *
     * @return the creditor
     */
    public BankAccount getCreditor() {
        return creditor;
    }

    /**
     * Sets creditor.
     *
     * @param creditor the creditor
     */
    public void setCreditor(BankAccount creditor) {
        this.creditor = creditor;
    }

    /**
     * Gets reference.
     *
     * @return the reference
     */
    public int getReference() {
        return reference;
    }

    /**
     * Sets reference.
     *
     * @param reference the reference
     */
    public void setReference(int reference) {
        this.reference = reference;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public double getAmount() {
        return Precision.round(amount,2);
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = Precision.round(amount,2);
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
