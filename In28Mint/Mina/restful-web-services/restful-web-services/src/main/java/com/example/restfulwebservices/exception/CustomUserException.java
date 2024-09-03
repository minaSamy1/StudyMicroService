package com.example.restfulwebservices.exception;

public class CustomUserException  extends  RuntimeException {

    public CustomUserException(String message) {
        super(message);
    }
}
