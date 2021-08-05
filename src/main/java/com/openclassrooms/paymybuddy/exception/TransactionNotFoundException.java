package com.openclassrooms.paymybuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Transaction not found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Element not Found")
public class TransactionNotFoundException extends Exception {
    /**
     * Instantiates a new Transaction not found exception.
     *
     * @param s the s
     */
    public TransactionNotFoundException(String s) {
        super(s);
    }
}
