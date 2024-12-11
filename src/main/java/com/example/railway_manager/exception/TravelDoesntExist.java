package com.example.railway_manager.exception;

public class TravelDoesntExist extends RuntimeException {
  public TravelDoesntExist(String message) {
    super(message);
  }
}
