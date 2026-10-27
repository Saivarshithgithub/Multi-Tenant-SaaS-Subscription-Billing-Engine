package com.example.demo.exception;

public class QuotaExceededException extends RuntimeException {
    public QuotaExceededException(String message) {
        super(message);
    }
}