package com.openclassrooms.paymybuddy.exception;

/**
 * The type Bank account already exist exception.
 */
public class BankAccountAlreadyExistException extends Exception {
    /**
     * Instantiates a new Bank account already exist exception.
     *
     * @param s the s
     */
    public BankAccountAlreadyExistException(String s) {
        super(s);
    }
}
