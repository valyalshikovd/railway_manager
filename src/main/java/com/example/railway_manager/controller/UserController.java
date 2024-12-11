package com.example.railway_manager.controller;


import com.example.railway_manager.dto.security.AddRoleDto;
import com.example.railway_manager.dto.security.ChangingPasswordDto;
import com.example.railway_manager.dto.security.RoleDto;
import com.example.railway_manager.dto.security.UserDto;
import com.example.railway_manager.exception.RoleDoesntExist;
import com.example.railway_manager.exception.UserNotFound;
import com.example.railway_manager.exception.WrongPasswordException;
import com.example.railway_manager.service.secure.UserService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;

    @PostMapping((ApiPaths.BASE_API + "/register"))
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            log.info("Registering user: {}", userDto);
            userService.registerUser(userDto);
            return ResponseEntity.ok("Successfully registered");
        }catch (Exception e) {
            log.error(e.getMessage() + userDto.toString());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping(ApiPaths.BASE_API +"/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        try {
            log.info("Logining user: {}", userDto);
            return ResponseEntity.ok(userService.verify(userDto));
        }catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Неверное имя пользователя или пароль");
        }
        catch (Exception e) {
            log.error(e.getMessage() + userDto.toString());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping(ApiPaths.BASE_API + "/registerAndLogin")
    public ResponseEntity<String> registerAndLogin(@RequestBody UserDto userDto){
        try {
            log.info("Registrating and logining user: {}", userDto);
            return ResponseEntity.ok(userService.registerAndVerifyUser(userDto));
        }catch (Exception e) {
            log.error(e.getMessage() + userDto.toString());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping((ApiPaths.ADMIN_API + "/registerWithRole"))
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

    @PostMapping(ApiPaths.ADMIN_API + "/deleteUser")
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

    @PostMapping(ApiPaths.USER_API + "/changePassword")
    public ResponseEntity<String> updatePassword(@RequestBody ChangingPasswordDto changingPasswordDto, @AuthenticationPrincipal UserDetails userDetails) {
        try {

            userService.updatePassword(
                    changingPasswordDto.getNewPassword(),
                    changingPasswordDto.getOldPassword(),
                    userDetails.getUsername());

            return ResponseEntity.ok().build();

        }catch (UserNotFound e){
            return ResponseEntity.unprocessableEntity().body("User not found " + e.getMessage());
        }catch (WrongPasswordException e){
            return ResponseEntity.unprocessableEntity().body("Wrong password " + e.getMessage());
        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PostMapping(ApiPaths.ADMIN_API + "/getRoles")
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


    @PostMapping(ApiPaths.ADMIN_API + "/addRoleToUser")
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

    @PostMapping(ApiPaths.ADMIN_API + "/deleteRoleFromUser")
    public ResponseEntity<String> deleteRoleFromUser(@RequestBody AddRoleDto addRoleDto){
        try {
            userService.deleteRole(addRoleDto.getRole(), addRoleDto.getUsername());
            return ResponseEntity.ok().build();
        }catch (UserNotFound e){
            return ResponseEntity.unprocessableEntity().body("User not found " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) String limit) {
        try {
            if(limit != null){
                int limitInt = Integer.parseInt(limit);
                return ResponseEntity.ok(userService.getAllUsers(limitInt));
            }
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (NumberFormatException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping(ApiPaths.ADMIN_API + "/getUser")
    public ResponseEntity<UserDto> getUser(@RequestBody UserDto user, @AuthenticationPrincipal UserDetails userDetails){
        try {
            return ResponseEntity.ok(userService.getUser(user.getUsername()));
        }catch (UserNotFound e){
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }



}
