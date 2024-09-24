package com.example.railway_manager.dto;

import com.example.railway_manager.model.security.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
@Builder
public class RoleDto {
    private Long id;
    private String name;
    private Set<UserDto> users = new HashSet<UserDto>();
}
