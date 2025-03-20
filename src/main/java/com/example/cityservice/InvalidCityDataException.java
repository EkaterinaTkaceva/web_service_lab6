package com.example.cityservice;

public class InvalidCityDataException extends RuntimeException {
    public InvalidCityDataException(String message) {
        super(message);
    }
}
