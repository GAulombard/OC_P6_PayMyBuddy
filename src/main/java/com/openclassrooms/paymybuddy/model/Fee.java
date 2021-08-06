package com.openclassrooms.paymybuddy.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The type Fee.
 */
@Entity
@Table(name = "fee")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fee_id")
    private int feeId;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "amount")
    private double amount;
    @Column(name = "rate100")
    private double rate100;

    @Column(name = "reference_transaction")
    private int transactionReference;

    @Column(name = "iban_account")
    private String account;

    /**
     * Gets transaction reference.
     *
     * @return the transaction reference
     */
    public int getTransactionReference() {
        return transactionReference;
    }

    /**
     * Sets transaction reference.
     *
     * @param transactionReference the transaction reference
     */
    public void setTransactionReference(int transactionReference) {
        this.transactionReference = transactionReference;
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * Sets account.
     *
     * @param account the account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Gets fee id.
     *
     * @return the fee id
     */
    public int getFeeId() {
        return feeId;
    }

    /**
     * Sets fee id.
     *
     * @param feeId the fee id
     */
    public void setFeeId(int feeId) {
        this.feeId = feeId;
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

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public double getAmount() {

        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets rate 100.
     *
     * @return the rate 100
     */
    public double getRate100() {
        return rate100;
    }

    /**
     * Sets rate 100.
     *
     * @param rate100 the rate 100
     */
    public void setRate100(double rate100) {
        this.rate100 = rate100;
    }
}
