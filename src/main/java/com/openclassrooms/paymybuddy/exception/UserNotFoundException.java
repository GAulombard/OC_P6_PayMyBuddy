package com.openclassrooms.paymybuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type User not found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Element not Found")
public class UserNotFoundException extends Exception{
    /**
     * Instantiates a new User not found exception.
     *
     * @param s the s
     */
    public UserNotFoundException(String s) {
        super(s);
    }
}
