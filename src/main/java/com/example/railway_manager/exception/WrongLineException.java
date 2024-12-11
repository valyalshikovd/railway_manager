package com.example.railway_manager.exception;

public class WrongLineException extends RuntimeException {
    public WrongLineException(String message) {
        super(message);
    }
}
