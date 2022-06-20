package com.msucil.dev.springbase.service.user;

public class UserExistException extends RuntimeException {

    public UserExistException() {
        // default constructor
    }

    public UserExistException(String message) {
        super(message);
    }
}
