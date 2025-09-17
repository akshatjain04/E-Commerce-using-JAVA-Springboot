package com.akshat.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * User Entity
 * Design Pattern: Builder Pattern, Value Object Pattern (address fields)
 * Security: Password is hashed, not stored in plain text
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    private String passwordHash;

    private String phone;

    @Builder.Default
    private Boolean isAdmin = false;

    // Address fields - could be extracted to separate Address value object
    private String street;
    private String apartment;
    private String zip;
    private String city;
    private String country;
}
