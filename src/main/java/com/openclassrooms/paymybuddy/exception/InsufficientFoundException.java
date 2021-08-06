package com.openclassrooms.paymybuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Insufficient found exception.
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Insufficient found")
public class InsufficientFoundException extends Exception {
    /**
     * Instantiates a new Insufficient found exception.
     *
     * @param s the s
     */
    public InsufficientFoundException(String s) {
        super(s);
    }
}
