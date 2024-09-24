package com.example.railway_manager.mapper;

import com.example.railway_manager.dto.RoleDto;
import com.example.railway_manager.model.security.Role;

import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleDto toDto(Role role){
        return RoleDto
                .builder()
                .name(role.getRolename())
                .build();
    }

    public static RoleDto toDtoWithFetch(Role role){
        return RoleDto
                .builder()
                .name(role.getRolename())
                .users(role.getUsers().stream().map(UserMapper::toDto).collect(Collectors.toSet()))
                .build();
    }

    public static Role toEntity(RoleDto roleDto){
        return Role
                .builder()
                .id(roleDto.getId())
                .rolename(roleDto.getName())
                .build();
    }
}
