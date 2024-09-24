package com.example.railway_manager.controller;


import com.example.railway_manager.dto.AddRoleDto;
import com.example.railway_manager.dto.ChangingPasswordDto;
import com.example.railway_manager.dto.RoleDto;
import com.example.railway_manager.dto.UserDto;
import com.example.railway_manager.exception.RoleDoesntExist;
import com.example.railway_manager.exception.UserNotFound;
import com.example.railway_manager.service.secure.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor

public class UserController{

    private final UserService userService;

    @PostMapping(("/register"))
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        try {
            log.info("Registering user: {}", userDto);
            return ResponseEntity.ok(userService.registerUser(userDto));
        }catch (Exception e) {
            log.error(e.getMessage() + userDto.toString());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        try {
            log.info("Logining user: {}", userDto);
            return ResponseEntity.ok(userService.verify(userDto));
        }catch (Exception e) {
            log.error(e.getMessage() + userDto.toString());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping("/registerAndLogin")
    public ResponseEntity<String> registerAndLogin(@RequestBody UserDto userDto){
        try {
            log.info("Registrating and logining user: {}", userDto);
            return ResponseEntity.ok(userService.registerAndVerifyUser(userDto));
        }catch (Exception e) {
            log.error(e.getMessage() + userDto.toString());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping(("/registerWithRole"))
    public ResponseEntity<String> registerWithRole(@RequestBody UserDto userDto) {
        try {
            userService.registerWithRole(userDto);
            return ResponseEntity.ok().build();
        } catch (RoleDoesntExist e) {
            return ResponseEntity.unprocessableEntity().body("Не существует роли " + e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage() + userDto.toString());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody UserDto userDto) {
        try {
            userService.deleteUser(userDto);
            return ResponseEntity.ok().build();
        }catch (UserNotFound e) {
            return ResponseEntity.unprocessableEntity().body("User not found " + e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> updatePassword(@RequestBody ChangingPasswordDto changingPasswordDto, @AuthenticationPrincipal UserDetails userDetails) {
        try {

            userService.updatePassword(
                    changingPasswordDto.getNewPassword(),
                    changingPasswordDto.getOldPassword(),
                    userDetails.getUsername());

            return ResponseEntity.ok().build();

        }catch (UserNotFound e){
            return ResponseEntity.unprocessableEntity().body("User not found " + e.getMessage());
        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PostMapping("/getRoles")
    public ResponseEntity<List<RoleDto>> getRoles(@RequestBody UserDto userDto) {
        try {
            List<RoleDto> res = userService.getRoles(userDto.getUsername());
            return ResponseEntity.ok(res);
        } catch (UserNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }


    @PostMapping("/addRoleToUser")
    public ResponseEntity<String> addRole(@RequestBody AddRoleDto addRoleDto){
        try {
            userService.addRole(addRoleDto.getRole(), addRoleDto.getUsername());
            return ResponseEntity.ok().build();
        } catch (UserNotFound e) {
            return ResponseEntity.unprocessableEntity().body("User not found " + e.getMessage());
        } catch (RoleDoesntExist e) {
            return ResponseEntity.unprocessableEntity().body("Role not found " + e.getMessage());
        }

    }

    @PostMapping("/deleteRoleFromUser")
    public ResponseEntity<String> deleteRoleFromUser(@RequestBody AddRoleDto addRoleDto){
        try {
            userService.deleteRole(addRoleDto.getRole(), addRoleDto.getUsername());
        }catch (UserNotFound e){
            return ResponseEntity.unprocessableEntity().body("User not found " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }



}
