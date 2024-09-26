package com.example.railway_manager.service.secure.impl;

import com.example.railway_manager.dto.security.RoleDto;
import com.example.railway_manager.dto.security.UserDto;
import com.example.railway_manager.exception.RoleDoesntExist;
import com.example.railway_manager.mapper.RoleMapper;
import com.example.railway_manager.mapper.UserMapper;
import com.example.railway_manager.model.security.Role;
import com.example.railway_manager.repository.RoleRepository;
import com.example.railway_manager.service.secure.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream().map(RoleMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<UserDto> findUsersByRole(RoleDto role) {
        Role roleEntity = roleRepository.findByRolename(role.getName());
        if(roleEntity == null) {
            throw new RoleDoesntExist(role.getName());
        }
        return roleEntity.getUsers().stream().map(UserMapper::toDto).toList();
    }
}
