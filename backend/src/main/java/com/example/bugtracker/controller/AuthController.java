package com.example.bugtracker.controller;

import com.example.bugtracker.dto.request.LoginRequest;
import com.example.bugtracker.dto.request.RegisterRequest;
import com.example.bugtracker.dto.response.ApiResponse;
import com.example.bugtracker.dto.response.LoginResponse;
import com.example.bugtracker.dto.response.UserResponse;
import com.example.bugtracker.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Optional<LoginResponse> response = authService.authenticate(request);

            if (response.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Login successful", response.get()));
            } else {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Invalid email or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Login failed: " + e.getMessage()));
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            UserResponse userResponse = authService.register(request);
            return ResponseEntity.ok(ApiResponse.success("Registration successful", userResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Registration failed: " + e.getMessage()));
        }
    }
}
