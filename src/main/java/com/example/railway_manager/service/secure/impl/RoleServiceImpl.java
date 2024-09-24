package com.example.railway_manager.service.secure.impl;

import com.example.railway_manager.model.security.Role;
import com.example.railway_manager.repository.RoleRepository;
import com.example.railway_manager.service.secure.RoleService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;

        roleRepository
                .save(
                        Role
                                .builder()
                                .rolename("USER")
                                .build()
                );

        roleRepository
                .save(
                        Role
                                .builder()
                                .rolename("ADMIN")
                                .build()
                );
    }
}
