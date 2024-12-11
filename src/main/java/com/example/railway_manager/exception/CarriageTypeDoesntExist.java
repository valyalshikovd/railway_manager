package com.example.railway_manager.exception;

public class CarriageTypeDoesntExist extends RuntimeException {
    public CarriageTypeDoesntExist(String message) {
        super(message);
    }
}
