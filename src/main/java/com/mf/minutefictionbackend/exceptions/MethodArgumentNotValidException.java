package com.mf.minutefictionbackend.exceptions;

import java.lang.reflect.Method;

public class MethodArgumentNotValidException extends RuntimeException{

    public MethodArgumentNotValidException() {
        super();
    }

    public MethodArgumentNotValidException(String message) {
        super(message);
    }

}
