package com.akshat.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Authentication Response DTO
 * Design Pattern: Data Transfer Object Pattern, Composite Pattern
 * Security: Contains JWT token and user information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private UserResponseDto user;
}
