// DTO Classes (Data Transfer Objects)

package com.akshat.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Login DTO
 * Design Pattern: Data Transfer Object Pattern
 * Security: Separate DTO for login to minimize data exposure
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}