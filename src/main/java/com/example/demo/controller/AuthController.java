package com.example.demo.controller;

import com.example.demo.dto.AuthLoginRequestDto;
import com.example.demo.dto.AuthRegisterRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    AuthResponseDto login(@RequestBody AuthLoginRequestDto authLoginRequestDto) {
        User user = authService.login(authLoginRequestDto.getUsername(), authLoginRequestDto.getPassword());
        UserResponseDto userResponseDto = userMapper.toResponseDto(user);
        String token = authService.generateToken(user);
        return new AuthResponseDto(token, userResponseDto);
    }

    @PostMapping("/register")
    AuthResponseDto register(@RequestBody AuthRegisterRequestDto authRegisterRequestDto) {
        User user = userMapper.toEntity(authRegisterRequestDto);
        user = authService.register(user);
        UserResponseDto userResponseDto = userMapper.toResponseDto(user);
        String token = authService.generateToken(user);
        return new AuthResponseDto(token, userResponseDto);
    }

    @GetMapping("/me")
    UserResponseDto me() {

        // Note how we can get the user from the security context
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }

        // We have our user!
        // Maybe better in a base controller method: getAuthUser()
        User user = (User) authentication.getPrincipal();
        return this.userMapper.toResponseDto(user);
    }
}
