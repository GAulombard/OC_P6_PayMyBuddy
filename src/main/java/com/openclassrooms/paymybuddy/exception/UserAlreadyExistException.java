package com.openclassrooms.paymybuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type User already exist exception.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Element already exist")
public class UserAlreadyExistException extends Exception {
    /**
     * Instantiates a new User already exist exception.
     *
     * @param m the m
     */
    public UserAlreadyExistException(String m) {
        super(m);
    }
}
