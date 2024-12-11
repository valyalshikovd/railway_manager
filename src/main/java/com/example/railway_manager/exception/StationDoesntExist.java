package com.example.railway_manager.exception;

public class StationDoesntExist extends RuntimeException {
    public StationDoesntExist(String message) {
        super(message);
    }
}
