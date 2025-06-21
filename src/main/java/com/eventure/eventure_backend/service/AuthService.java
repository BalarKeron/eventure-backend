package com.eventure.eventure_backend.service;

import com.eventure.eventure_backend.model.User;
import com.eventure.eventure_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // Register
    public User registerUser(User user) {
        user.setRole("USER");
        user.setApproved(true);
        return userRepository.save(user);
    }

    // Login
    public Optional<User> loginUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }

    // Forgot Password
    public Optional<User> forgotPassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        userOptional.ifPresent(user -> {
            user.setPassword(newPassword);
            userRepository.save(user);
        });
        return userOptional;
    }

    // Request Host
    public Optional<User> requestHost(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        userOptional.ifPresent(user -> {
            user.setRole("HOST");
            user.setApproved(false); // Host request pending
            userRepository.save(user);
        });
        return userOptional;
    }

    // Approve Host
    public Optional<User> approveHost(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        userOptional.ifPresent(user -> {
            if ("HOST".equals(user.getRole())) {
                user.setApproved(true);
                userRepository.save(user);
            }
        });
        return userOptional;
    }
}
