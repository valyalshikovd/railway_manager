package com.example.railway_manager.repository.security;

import com.example.railway_manager.model.security.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

     Users findByUsername(String username);
}
