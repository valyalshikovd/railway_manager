package com.example.railway_manager.service.secure.impl;

import com.example.railway_manager.dto.security.RoleDto;
import com.example.railway_manager.dto.security.UserDto;
import com.example.railway_manager.exception.RoleDoesntExist;
import com.example.railway_manager.exception.UserNotFound;
import com.example.railway_manager.exception.WrongPasswordException;
import com.example.railway_manager.mapper.RoleMapper;
import com.example.railway_manager.mapper.UserMapper;
import com.example.railway_manager.model.security.Role;
import com.example.railway_manager.model.security.Users;
import com.example.railway_manager.repository.security.RoleRepository;
import com.example.railway_manager.repository.security.UserRepository;
import com.example.railway_manager.service.secure.JWTService;
import com.example.railway_manager.service.secure.RoleService;
import com.example.railway_manager.service.secure.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service

public class UserServiceImpl implements UserService {

    private final JWTService jwtService;
    private final AuthenticationManager authManager;
    private final UserRepository repo;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public UserServiceImpl(JWTService jwtService, AuthenticationManager authManager, UserRepository repo, RoleRepository roleRepository, UserRepository userRepository, RoleService roleService) {
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.repo = repo;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleService = roleService;
        System.out.println("UserServiceImpl");
        Role user = Role
                .builder()
                .rolename("USER")
                .build();
        Role admin = Role
                .builder()
                .rolename("ADMIN")
                .build();
        System.out.println(user);
        System.out.println(admin);

//        roleRepository.save(user);
//        roleRepository.save(admin);
//
//
//       userRepository
//                .save(
//                        Users.builder()
//                                .username("DefaultAdmin")
//                                .password(encoder.encode("0000"))
//                                .roles(new HashSet<>(Arrays.asList(user, admin)))
//                                .build()
//                );

    }

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

    @Override
    public UserDto registerWithRole(UserDto user) {
        Users userEntity = UserMapper.toEntity(user);
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setRoles(new HashSet<>());
        try {
            log.info("Сохранение сущности User");
            user.getRoles().forEach(
                    role -> {
                        Role roleEntity = roleRepository.findByRolename(role.getName());
                        if(roleEntity != null){
                            userEntity.getRoles().add(roleEntity);
                        }else{
                            throw new RoleDoesntExist(role.getName());
                        }
                    }
            );
            repo.save(userEntity);
            return user;
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("Не удалось сохранить сущность User");
            return null;
        }
    }

    @Override
    public boolean deleteUser(UserDto user) {


            Users userEntity = userRepository.findByUsername(user.getUsername());
            if(userEntity == null){
                throw new UserNotFound(user.getUsername());
            }

            try {
                userRepository.delete(userEntity);
            }  catch (Exception e){
                log.error(e.getMessage());
                log.error("User " + user.getUsername() + " не был удален");
                return false;
            }

            log.info("User " + user.getUsername() + " был удален");
            return true;
    }

    @Override
    public UserDto updatePassword(String newPassword, String oldPassword, String username) {
        Users userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            throw new UserNotFound(username);
        }
        if(encoder.matches(oldPassword, userEntity.getPassword())){
            userEntity.setPassword(encoder.encode(newPassword));
            userRepository.save(userEntity);
            return UserDto.builder().username(username).password(newPassword).build();
        }

        throw new WrongPasswordException(oldPassword);
    }

    @Override
    @Transactional
    public List<RoleDto> getRoles(String name) {

        Users userEntity = userRepository.findByUsername(name);
        if(userEntity == null){
            throw new UserNotFound(name);
        }

        return userEntity
                .getRoles()
                .stream()
                .map(RoleMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void addRole(RoleDto role, String username) {

        Role roleEntity  = roleRepository.findByRolename(role.getName());

        if(roleEntity == null){
            throw new RoleDoesntExist(role.getName());
        }

        Users userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            throw new UserNotFound(username);
        }
        userEntity.getRoles().add(roleEntity);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void deleteRole(RoleDto role, String username) {


        Users userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            throw new UserNotFound(username);
        }
        userEntity
                .getRoles()
                .remove(
                        userEntity
                                .getRoles()
                                .stream()
                                .filter(
                                        role1 -> Objects.equals(role1.getRolename(), role.getName()))
                                .findFirst().
                                get());

        userRepository.save(userEntity);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> getAllUsers(int limit) {
        return userRepository
                .findAll()
                .stream()
                .map(UserMapper::toDto)
                .limit(limit)
                .toList();
    }

    @Override
    public UserDto getUser(String username) {
        Users user = userRepository.findByUsername(username);
        if(user == null){
            throw new UserNotFound(username);
        }
        return UserMapper.toDto(user);
    }


}
