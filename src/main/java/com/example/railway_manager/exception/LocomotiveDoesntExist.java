package com.example.railway_manager.exception;

public class LocomotiveDoesntExist extends RuntimeException {
    public LocomotiveDoesntExist(String message) {
        super(message);
    }
}
