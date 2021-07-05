package com.openclassrooms.paymybuddy.exception;

public class BankAccountAlreadyExistException extends Exception {
    public BankAccountAlreadyExistException(String s) {
        super(s);
    }
}
