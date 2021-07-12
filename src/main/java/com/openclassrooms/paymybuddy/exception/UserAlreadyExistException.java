package com.openclassrooms.paymybuddy.exception;

/**
 * The type User already exist exception.
 */
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
