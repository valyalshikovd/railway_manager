package com.example.railway_manager.repository.security;

import com.example.railway_manager.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRolename(String role_name);
}
