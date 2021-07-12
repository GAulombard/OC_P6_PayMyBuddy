package com.openclassrooms.paymybuddy.exception;

/**
 * The type User not found exception.
 */
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
