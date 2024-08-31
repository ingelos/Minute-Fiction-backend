package com.mf.minutefictionbackend.exceptions;

public class NotAllowedToUpdatePublishedStoryException extends RuntimeException{
    public NotAllowedToUpdatePublishedStoryException(String message) {
        super(message);
    }
}
