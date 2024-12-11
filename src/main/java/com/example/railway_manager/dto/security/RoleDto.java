package com.example.railway_manager.dto.security;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
@Builder
public class RoleDto {
    private Long id;
    private String name;
    private Set<UserDto> users;
}
