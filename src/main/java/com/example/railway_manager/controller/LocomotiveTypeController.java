package com.example.railway_manager.controller;


import com.example.railway_manager.dto.railways.LocomotiveDTO;
import com.example.railway_manager.dto.railways.LocomotiveTypeDTO;
import com.example.railway_manager.exception.LocomotiveTypeDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.service.railway.LocomotiveTypeService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocomotiveTypeController {
    private final LocomotiveTypeService locomotiveTypeService;


    @PostMapping(ApiPaths.ADMIN_API + "/createLocomotiveType")
    public ResponseEntity<LocomotiveTypeDTO> createLocomotiveType(@RequestBody LocomotiveTypeDTO locomotiveTypeDTO) {
        try {
            return ResponseEntity.ok(locomotiveTypeService.createLocomotiveType(locomotiveTypeDTO));
        } catch (WrongDTOException e) {
            return ResponseEntity.badRequest().body(locomotiveTypeDTO);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(locomotiveTypeDTO);
        }
    }

    @PostMapping(ApiPaths.ADMIN_API + "/updateLocomotiveType")
    public ResponseEntity<LocomotiveTypeDTO> updateLocomotiveType(
            @RequestBody LocomotiveTypeDTO locomotiveTypeDTO
    ) {
        try {
            return ResponseEntity.ok(locomotiveTypeService.updateLocomotiveType(locomotiveTypeDTO));
        } catch (LocomotiveTypeDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(locomotiveTypeDTO);
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/deleteLocomotiveType")
    public ResponseEntity<LocomotiveTypeDTO> deleteLocomotiveType(
            @RequestParam(name = "id") String id
    ) {
        try {
            Long idL = Long.getLong(id);
            if (idL == null) {
                return ResponseEntity.unprocessableEntity().body(null);
            }
            return ResponseEntity.ok(locomotiveTypeService.deleteLocomotiveType(idL));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        } catch (LocomotiveTypeDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getLocomotiveTypeByName")
    public ResponseEntity<LocomotiveTypeDTO> getLocomotiveTypeByName(
            @RequestParam(name = "name") String name
    ) {
        try {
            return ResponseEntity.ok(locomotiveTypeService.getLocomotiveTypeByName(name));
        } catch (LocomotiveTypeDoesntExist e) {
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getAllLocomotiveType")
    public ResponseEntity<List<LocomotiveTypeDTO>> getLocomotiveAllLocomotiveTypes() {
        try {
            return  ResponseEntity.ok(locomotiveTypeService.getAllLocomotiveTypes());
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getAllLocomotiveByType")
    public ResponseEntity<List<LocomotiveDTO>> getLocomotiveByType(
            @RequestParam(name = "locomotiveType") String locomotiveType
    ) {
        try {
            return ResponseEntity.ok(locomotiveTypeService.getLocomotivesByType(Long.parseLong(locomotiveType)));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (LocomotiveTypeDoesntExist e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }


}
