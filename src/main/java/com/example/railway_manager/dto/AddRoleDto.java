package com.example.railway_manager.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRoleDto {

    private RoleDto role;
    private String username;
}
