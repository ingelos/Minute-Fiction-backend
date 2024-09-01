package com.mf.minutefictionbackend.exceptions;

import com.mf.minutefictionbackend.models.AuthorProfile;

public class AuthorProfileAlreadyExistsException extends RuntimeException{
    public AuthorProfileAlreadyExistsException(String message) {
        super(message);
    }
}
