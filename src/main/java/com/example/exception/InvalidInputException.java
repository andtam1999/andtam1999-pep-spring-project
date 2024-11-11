package com.example.exception;

public class InvalidInputException extends Exception {

    String invalidParam;

    public InvalidInputException(String errorMsg) {
        super(errorMsg);
    }
}
