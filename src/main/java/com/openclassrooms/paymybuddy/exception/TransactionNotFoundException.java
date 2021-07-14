package com.openclassrooms.paymybuddy.exception;

public class TransactionNotFoundException extends Throwable {
    public TransactionNotFoundException(String s) {
        super(s);
    }
}
