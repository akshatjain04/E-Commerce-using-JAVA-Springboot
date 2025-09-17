package com.akshat.ecommerce.contfoller;

import com.ecommerce.dto.request.UserLoginDto;
import com.ecommerce.dto.request.UserRegistrationDto;
import com.ecommerce.dto.response.AuthResponseDto;
import com.ecommerce.dto.response.UserResponseDto;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * User Controller
 * Design Pattern: MVC Pattern, Command Pattern (different operations)
 * Security: Authentication endpoints, role-based access control
 */
@RestController
@RequestMapping("${api.url}/users")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        UserResponseDto registeredUser = userService.registerUser(userRegistrationDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@Valid @RequestBody UserLoginDto userLoginDto) {
        AuthResponseDto authResponse = userService.loginUser(userLoginDto);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @userService.getUserById(#id).email")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == @userService.getUserById(#id).email")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        UserResponseDto updatedUser = userService.updateUser(id, userRegistrationDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
