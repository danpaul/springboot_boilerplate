package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.enums.Roles;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        return this.userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        throw new IllegalArgumentException("Invalid username or password");
    }

    public String generateToken(User user) {
        return "token-123";
    }
}
