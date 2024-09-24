package com.example.railway_manager.exception;

public class RoleDoesntExist extends RuntimeException {
    public RoleDoesntExist(String message) {
        super(message);
    }
}
