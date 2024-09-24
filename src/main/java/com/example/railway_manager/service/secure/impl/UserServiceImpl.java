package com.example.railway_manager.service.secure.impl;

import com.example.railway_manager.dto.UserDto;
import com.example.railway_manager.mapper.UserMapper;
import com.example.railway_manager.model.security.Users;
import com.example.railway_manager.repository.RoleRepository;
import com.example.railway_manager.repository.UserRepository;
import com.example.railway_manager.service.secure.JWTService;
import com.example.railway_manager.service.secure.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JWTService jwtService;

    private final AuthenticationManager authManager;

    private final UserRepository repo;
    private final RoleRepository roleRepository;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserDto registerUser(UserDto user) {
        Users userEntity = UserMapper.toEntity(user);
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setRoles(new HashSet<>());
        try {
            userEntity.getRoles().add(roleRepository.findByRolename("USER"));
            repo.save(userEntity);
            return user;
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("Не удалось сохранить сущность User");
            return null;
        }
    }

    public String verify(UserDto user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }


    public String registerAndVerifyUser(UserDto user) {
        Users userEntity = UserMapper.toEntity(user);
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setRoles(new HashSet<>());
        try {
            userEntity.getRoles().add(roleRepository.findByRolename("USER"));
            repo.save(userEntity);
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername());
            }
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("Не удалось сохранить или валидикровать сущность User");
            return null;
        }
        return null;
    }
}
