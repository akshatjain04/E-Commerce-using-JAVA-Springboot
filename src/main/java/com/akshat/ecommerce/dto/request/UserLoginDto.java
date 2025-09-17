// DTO Classes (Data Transfer Objects)

package com.akshat.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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