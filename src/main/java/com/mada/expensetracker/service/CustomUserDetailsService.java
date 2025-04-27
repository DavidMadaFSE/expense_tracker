package com.mada.expensetracker.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mada.expensetracker.entity.User;
import com.mada.expensetracker.repository.UserRepository;

@Service
public class CustomUserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Load a User from database (find by email), used during login authentication
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }
}
