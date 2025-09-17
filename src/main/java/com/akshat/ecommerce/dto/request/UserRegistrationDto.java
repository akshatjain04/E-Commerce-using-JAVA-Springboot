package com.akshat.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * User Registration DTO
 * Design Pattern: Data Transfer Object Pattern, Builder Pattern
 * Validation: Input validation rules for user registration
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String phone;
    private Boolean isAdmin;
    private String street;
    private String apartment;
    private String zip;
    private String city;
    private String country;
}