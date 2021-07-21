package com.openclassrooms.paymybuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Bank account not found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Element not Found")
public class BankAccountNotFoundException extends Exception {

    /**
     * Instantiates a new Bank account not found exception.
     *
     * @param s the s
     */
    public BankAccountNotFoundException(String s) {
        super(s);
    }
}
