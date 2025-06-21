package com.eventure.eventure_backend.controller;

import com.eventure.eventure_backend.dto.LoginRequest;
import com.eventure.eventure_backend.dto.LoginResponse;
import com.eventure.eventure_backend.model.User;
import com.eventure.eventure_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Registration
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.registerUser(user);
    }

    // Login
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = authService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (user.isPresent()) {
            return new LoginResponse(user.get().getRole(), user.get().isApproved());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    // Forgot Password
    @PostMapping("/forgotPassword")
    public User forgotPassword(@RequestParam String email, @RequestParam String newPassword) {
        return authService.forgotPassword(email, newPassword)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Host Request
    @PostMapping("/requestHost")
    public User requestHost(@RequestParam String email) {
        return authService.requestHost(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Approve Host (Admin only)
    @PostMapping("/approveHost")
    public User approveHost(@RequestParam String email) {
        return authService.approveHost(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
