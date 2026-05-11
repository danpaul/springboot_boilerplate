package com.example.demo.controller;

import com.example.demo.dto.AdminUserDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("")
    Iterable<UserResponseDto> getAll() {
        return this.userMapper.toResponseDto(this.userService.findAll());
    }

    @GetMapping("/{id}")
    UserResponseDto get(@PathVariable Long id) {
        Optional<User> user = this.userService.findById(id);
        if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        return this.userMapper.toResponseDto(user.get());
    }

    @PostMapping("")
    UserResponseDto create(@RequestBody AdminUserDto adminUserDto) {
        User user = this.userMapper.toEntity(adminUserDto);
        return this.userMapper.toResponseDto(this.userService.save(user));
    }

    @PutMapping("/{id}")
    UserResponseDto update(@RequestBody AdminUserDto adminUserDto, @PathVariable Long id) {
        Optional<User> existingUser = this.userService.findById(id);

        if (existingUser.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        User user = this.userMapper.toEntity(adminUserDto);
        user.setId(existingUser.get().getId());

        return this.userMapper.toResponseDto(this.userService.update(user));
    }

    @DeleteMapping("/{id}")
    Object delete(@PathVariable Long id) {
        this.userService.delete(id);
        return new Object();
    }
}
