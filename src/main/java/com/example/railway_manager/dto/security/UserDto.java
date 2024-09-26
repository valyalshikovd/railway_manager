package com.example.railway_manager.dto.security;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Set<RoleDto> roles = new HashSet<RoleDto>();
}
