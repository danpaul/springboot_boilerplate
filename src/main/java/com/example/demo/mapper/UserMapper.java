package com.example.demo.mapper;

import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toResponseDto(User user);

    Iterable<UserResponseDto> toResponseDto(Iterable<User> users);
}
