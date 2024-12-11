package com.example.railway_manager.exception;

public class LocomotiveTypeDoesntExist extends RuntimeException {
    public LocomotiveTypeDoesntExist(String message) {
        super(message);
    }
}
