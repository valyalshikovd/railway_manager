package com.example.railway_manager.controller;


import com.example.railway_manager.dto.security.RoleDto;
import com.example.railway_manager.dto.security.UserDto;
import com.example.railway_manager.exception.RoleDoesntExist;
import com.example.railway_manager.service.secure.RoleService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.ADMIN_API)
public class RoleController {

    private final RoleService roleService;



    @PostMapping("/getUsersByRole")
    public ResponseEntity<List<UserDto>> getUsersByRole(@RequestBody RoleDto role) {
        try {
            return ResponseEntity.ok(roleService.findUsersByRole(role));
        }catch (RoleDoesntExist e){
            return ResponseEntity.badRequest().body(null);
        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }


    @GetMapping("/getAllRoles")
    public ResponseEntity<List<RoleDto>> getRoles() {
        try {
            return ResponseEntity.ok(roleService.getAllRoles());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
