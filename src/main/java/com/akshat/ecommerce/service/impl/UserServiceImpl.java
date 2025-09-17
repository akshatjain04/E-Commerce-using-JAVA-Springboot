package com.akshat.ecommerce.service.impl;

import com.akshat.ecommerce.dto.request.UserLoginDto;
import com.akshat.ecommerce.dto.request.UserRegistrationDto;
import com.akshat.ecommerce.dto.response.AuthResponseDto;
import com.akshat.ecommerce.dto.response.UserResponseDto;
import com.akshat.ecommerce.exception.BadRequestException;
import com.akshat.ecommerce.exception.ResourceNotFoundException;
import com.akshat.ecommerce.exception.UnauthorizedException;
import com.akshat.ecommerce.model.User;
import com.akshat.ecommerce.repository.UserRepository;
import com.akshat.ecommerce.secuity.JwtUtil;
import com.akshat.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User Service Implementation
 * Design Pattern: Service Layer Pattern, Strategy Pattern (authentication)
 * Security: Password encryption, JWT token generation
 * SOLID: Single Responsibility, Dependency Inversion
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDto registerUser(UserRegistrationDto userRegistrationDto) {
        if (userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            throw new BadRequestException("User with email '" + userRegistrationDto.getEmail() + "' already exists");
        }

        User user = modelMapper.map(userRegistrationDto, User.class);
        user.setPasswordHash(passwordEncoder.encode(userRegistrationDto.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Override
    public AuthResponseDto loginUser(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        UserResponseDto userResponse = modelMapper.map(user, UserResponseDto.class);

        return AuthResponseDto.builder()
                .token(token)
                .user(userResponse)
                .build();
    }

    @Override
    public UserResponseDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUser(String id, UserRegistrationDto userRegistrationDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!existingUser.getEmail().equals(userRegistrationDto.getEmail())
                && userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            throw new BadRequestException("User with email '" + userRegistrationDto.getEmail() + "' already exists");
        }

        modelMapper.map(userRegistrationDto, existingUser);
        if (userRegistrationDto.getPassword() != null && !userRegistrationDto.getPassword().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(userRegistrationDto.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}