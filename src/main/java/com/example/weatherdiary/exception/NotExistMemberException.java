package com.example.weatherdiary.exception;

public class NotExistMemberException extends RuntimeException {

    public NotExistMemberException() {
    }

    public NotExistMemberException(String message) {
        super(message);
    }
}
