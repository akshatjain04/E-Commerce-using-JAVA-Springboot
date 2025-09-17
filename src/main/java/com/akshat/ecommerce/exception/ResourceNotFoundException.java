package com.akshat.ecommerce.exception;

/**
 * Resource Not Found Exception
 * Design Pattern: Custom Exception Pattern
 * SOLID: Single Responsibility - specific exception for resource not found
 * scenarios
 */
public class ResourceNotFoundException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
