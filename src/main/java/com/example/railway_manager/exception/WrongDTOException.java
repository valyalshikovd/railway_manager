package com.example.railway_manager.exception;

public class WrongDTOException extends RuntimeException {
    public WrongDTOException(String message) {
        super(message);
    }
}
