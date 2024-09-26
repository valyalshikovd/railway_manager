package com.example.railway_manager.service.secure;

import com.example.railway_manager.dto.security.RoleDto;
import com.example.railway_manager.dto.security.UserDto;

import java.util.List;

public interface RoleService {


    List<RoleDto> getAllRoles();
    List<UserDto> findUsersByRole(RoleDto role);
}
