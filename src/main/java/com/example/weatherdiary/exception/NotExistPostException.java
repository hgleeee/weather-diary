package com.example.weatherdiary.exception;

public class NotExistPostException extends RuntimeException {

    public NotExistPostException() {
    }

    public NotExistPostException(String message) {
        super(message);
    }
}
