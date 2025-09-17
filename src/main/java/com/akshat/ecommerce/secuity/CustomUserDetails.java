package com.akshat.ecommerce.secuity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Custom User Details Implementation
 * Design Pattern: Adapter Pattern
 * Security: Implements Spring Security UserDetails interface
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private String id;
    private String email;
    private String password;
    private Boolean isAdmin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isAdmin != null && isAdmin) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}