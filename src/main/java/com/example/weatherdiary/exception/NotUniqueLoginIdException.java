package com.example.weatherdiary.exception;

public class NotUniqueLoginIdException extends RuntimeException {

    public NotUniqueLoginIdException() {
    }

    public NotUniqueLoginIdException(String message) {
        super(message);
    }
}
