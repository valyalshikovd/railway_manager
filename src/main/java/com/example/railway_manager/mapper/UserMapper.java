package com.example.railway_manager.mapper;

import com.example.railway_manager.dto.security.UserDto;
import com.example.railway_manager.model.security.Users;
import jakarta.transaction.Transactional;

import java.util.stream.Collectors;

@Transactional
public class UserMapper {

    public static UserDto toDto(Users user){
        return UserDto
                .builder()
                .username(user.getUsername())
                .password(null)
                .build();
    }

    public static UserDto toDtoWithFetching(Users user){
        return UserDto
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(RoleMapper::toDto).collect(Collectors.toSet()))
                .build();
    }
    public static Users toEntity(UserDto dto){
        return Users
                .builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }
}
