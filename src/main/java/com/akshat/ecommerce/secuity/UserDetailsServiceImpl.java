package com.akshat.ecommerce.secuity;

import com.akshat.ecommerce.model.User;
import com.akshat.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom User Details Service
 * Design Pattern: Adapter Pattern (adapts User to UserDetails)
 * Security: Bridge between Spring Security and our User model
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return CustomUserDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPasswordHash())
                .isAdmin(user.getIsAdmin())
                .build();
    }
}
