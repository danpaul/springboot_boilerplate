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
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(Roles.ROLE_USER));

        return this.userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new IllegalArgumentException("Invalid username or password");
    }

    public String generateToken(User user) {
        return this.jwtUtil.generateToken(user.getUsername());
    }
}
