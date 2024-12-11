package com.example.railway_manager.exception;

public class SegmentDoesntExist extends RuntimeException {
    public SegmentDoesntExist(String message) {
        super(message);
    }
}
