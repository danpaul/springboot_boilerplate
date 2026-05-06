package com.example.demo.mapper;

import com.example.demo.dto.AdminUserDto;
import com.example.demo.dto.AuthRegisterRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toResponseDto(User user);

    Iterable<UserResponseDto> toResponseDto(Iterable<User> users);

    AdminUserDto toAdminDto(User user);

    Iterable<AdminUserDto> toAdminDto(Iterable<User> users);

    // @Mapping(target = "authorities", ignore = true)
    // TODO
    User toEntity(AdminUserDto adminUserDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
        // @Mapping(target = "authorities", ignore = true)
        // TODO
    User toEntity(AuthRegisterRequestDto authRegisterRequestDto);

    Iterable<User> toEntity(Iterable<AdminUserDto> adminUserDtos);
}
