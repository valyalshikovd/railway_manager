package com.example.railway_manager.exception;

public class LineDoesntExist extends RuntimeException {
    public LineDoesntExist(String message) {
        super(message);
    }
}
