package com.openclassrooms.paymybuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Bank account already exist exception.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Element already exist")
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
