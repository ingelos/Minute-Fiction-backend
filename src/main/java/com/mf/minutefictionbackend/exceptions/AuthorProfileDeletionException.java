package com.mf.minutefictionbackend.exceptions;

public class AuthorProfileDeletionException extends RuntimeException {

    public AuthorProfileDeletionException() {
        super();
    }

    public AuthorProfileDeletionException(String message) {
        super(message);
    }
}
