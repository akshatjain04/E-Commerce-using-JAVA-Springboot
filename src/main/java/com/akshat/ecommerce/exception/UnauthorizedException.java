package com.akshat.ecommerce.exception;

/**
 * Unauthorized Exception
 * Design Pattern: Custom Exception Pattern
 * Security: Specific exception for authentication/authorization failures
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
