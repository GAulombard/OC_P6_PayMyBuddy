package com.openclassrooms.paymybuddy.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String m) {
        super(m);
    }
}
