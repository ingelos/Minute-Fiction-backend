package com.mf.minutefictionbackend.exceptions;

import com.mf.minutefictionbackend.models.User;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
