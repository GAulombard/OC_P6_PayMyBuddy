package com.openclassrooms.paymybuddy.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "fee")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fee_id")
    private int feeId;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "amount")
    private double amount;
    @Column(name = "rate100")
    private double rate100;

    public int getFeeId() {
        return feeId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate100() {
        return rate100;
    }

    public void setRate100(double rate100) {
        this.rate100 = rate100;
    }
}
