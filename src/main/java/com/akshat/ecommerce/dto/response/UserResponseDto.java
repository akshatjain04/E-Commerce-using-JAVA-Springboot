package com.akshat.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Response DTO
 * Design Pattern: Data Transfer Object Pattern
 * Security: Excludes sensitive data like password hash
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String id;
    private String name;
    private String email;
    private String phone;
    private Boolean isAdmin;
    private String street;
    private String apartment;
    private String zip;
    private String city;
    private String country;
}