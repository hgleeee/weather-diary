package com.example.weatherdiary.exception;

public class InvalidValueException extends RuntimeException {

    public InvalidValueException() {
    }

    public InvalidValueException(String message) {
        super(message);
    }
}
