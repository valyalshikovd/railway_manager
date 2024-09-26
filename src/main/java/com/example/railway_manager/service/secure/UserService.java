package com.example.railway_manager.service.secure;

import com.example.railway_manager.dto.security.RoleDto;
import com.example.railway_manager.dto.security.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto user);
    String verify(UserDto user);
    String registerAndVerifyUser(UserDto user);
    UserDto registerWithRole(UserDto user);
    boolean deleteUser(UserDto user);
    UserDto updatePassword(String newPassword, String oldPassword, String username);
    List<RoleDto> getRoles(String name);
    void addRole(RoleDto role, String username);
    void deleteRole(RoleDto role, String username);
    List<UserDto> getAllUsers();
    List<UserDto> getAllUsers(int limit);
    UserDto getUser(String username);
}
