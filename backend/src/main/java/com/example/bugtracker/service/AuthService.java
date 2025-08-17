package com.example.bugtracker.service;

import com.example.bugtracker.dto.request.LoginRequest;
import com.example.bugtracker.dto.request.RegisterRequest;
import com.example.bugtracker.dto.response.LoginResponse;
import com.example.bugtracker.dto.response.UserResponse;
import com.example.bugtracker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<LoginResponse> authenticate(LoginRequest request) {
        Optional<User> userOpt = userService.getUserByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                UserResponse userResponse = new UserResponse(user);
                // Simple token - just user ID for demo
                String token = "user-" + user.getId();
                LoginResponse loginResponse = new LoginResponse(token, userResponse);
                return Optional.of(loginResponse);
            }
        }
        return Optional.empty();
    }

    public UserResponse register(RegisterRequest request) {
        return userService.createUser(request);
    }
}