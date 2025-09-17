package com.akshat.ecommerce.exception;

/**
 * Bad Request Exception
 * Design Pattern: Custom Exception Pattern
 * SOLID: Single Responsibility - specific exception for bad request scenarios
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
