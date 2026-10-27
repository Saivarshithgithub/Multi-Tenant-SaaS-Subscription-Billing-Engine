package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuotaExceededException.class)
    public ResponseEntity<Map<String, Object>> handleQuotaExceeded(QuotaExceededException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.TOO_MANY_REQUESTS.value()); // 429 status
        errorResponse.put("error", "Quota Exceeded");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleGeneralRuntime(RuntimeException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value()); // 400 status
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}