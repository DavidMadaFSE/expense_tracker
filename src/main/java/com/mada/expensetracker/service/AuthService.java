package com.mada.expensetracker.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mada.expensetracker.dto.AuthResponse;
import com.mada.expensetracker.dto.LoginRequest;
import com.mada.expensetracker.dto.RegisterRequest;
import com.mada.expensetracker.entity.User;
import com.mada.expensetracker.repository.UserRepository;
import com.mada.expensetracker.security.JwtUtil;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Register a new user, hash password, save to database
    public void register(RegisterRequest request) {
        User newUser = new User();
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setFullName(request.fullName());

        userRepository.save(newUser);

        System.out.println("User registered successfully");
    }

    // Verify user email and password, return JWT token
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        System.out.println("Token for logging in: " + token);

        return new AuthResponse(token);
    }
    
}
