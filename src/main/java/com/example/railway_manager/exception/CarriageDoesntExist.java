package com.example.railway_manager.exception;

public class CarriageDoesntExist extends RuntimeException {
    public CarriageDoesntExist(String message) {
        super(message);
    }
}
