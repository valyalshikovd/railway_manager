package com.example.railway_manager.exception;

public class TrainDoesntExist extends RuntimeException {
    public TrainDoesntExist(String message) {
        super(message);
    }
}
