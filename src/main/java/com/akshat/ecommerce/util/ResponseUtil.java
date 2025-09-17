package com.akshat.ecommerce.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Response Utility Class
 * Design Pattern: Utility Pattern, Builder Pattern
 * DRY: Standardized API response format
 */
@Component
public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return success(data, "Operation successful");
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return created(data, "Resource created successfully");
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(status).body(response);
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        private LocalDateTime timestamp;
        private Map<String, Object> metadata = new HashMap<>();
    }
}
