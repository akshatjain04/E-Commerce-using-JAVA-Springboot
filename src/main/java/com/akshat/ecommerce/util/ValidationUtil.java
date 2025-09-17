package com.akshat.ecommerce.util;

import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

/**
 * Validation Utility Class
 * Design Pattern: Utility Pattern, Strategy Pattern
 * SOLID: Single Responsibility - validation logic only
 */
@Component
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^[\\+]?[1-9]?[0-9]{7,15}$");

    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> "!@#$%^&*(),.?\":{}|<>".indexOf(ch) >= 0);

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}