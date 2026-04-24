package com.example.demo.controller;

import com.example.demo.dto.AuthLoginRequestDto;
import com.example.demo.dto.AuthRegisterRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.UserResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @PostMapping("/login")
    AuthResponseDto login(@RequestBody AuthLoginRequestDto authLoginRequestDto) {
        UserResponseDto user = new UserResponseDto(1L, authLoginRequestDto.getUsername());
        return new AuthResponseDto("dummy-login-token", user);
    }

    @PostMapping("/register")
    AuthResponseDto register(@RequestBody AuthRegisterRequestDto authRegisterRequestDto) {
        UserResponseDto user = new UserResponseDto(2L, authRegisterRequestDto.getUsername());
        return new AuthResponseDto("dummy-register-token", user);
    }

    @GetMapping("/me")
    UserResponseDto me() {
        return new UserResponseDto(1L, "dummy-user");
    }
}
