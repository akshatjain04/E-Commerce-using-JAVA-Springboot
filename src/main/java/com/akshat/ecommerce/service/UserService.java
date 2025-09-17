package com.akshat.ecommerce.service;

import com.akshat.ecommerce.dto.request.UserLoginDto;
import com.akshat.ecommerce.dto.request.UserRegistrationDto;
import com.akshat.ecommerce.dto.response.AuthResponseDto;
import com.akshat.ecommerce.dto.response.UserResponseDto;

import java.util.List;

/**
 * User Service Interface
 * Design Pattern: Strategy Pattern for different authentication methods
 * Security: Authentication and user management abstraction
 */
public interface UserService {
    UserResponseDto registerUser(UserRegistrationDto userRegistrationDto);

    AuthResponseDto loginUser(UserLoginDto userLoginDto);

    UserResponseDto getUserById(String id);

    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(String id, UserRegistrationDto userRegistrationDto);

    void deleteUser(String id);
}
