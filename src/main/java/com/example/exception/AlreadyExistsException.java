package com.example.exception;

public class AlreadyExistsException extends Exception {
    
    public AlreadyExistsException(String errorMsg) {
        super(errorMsg);
    }
}