package com.example.railway_manager.controller;

import com.example.railway_manager.dto.railways.CarriageDTO;
import com.example.railway_manager.dto.railways.CarriageTypeDTO;
import com.example.railway_manager.exception.CarriageTypeDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.service.railway.CarriageTypeService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarriageTypeController {
    private final CarriageTypeService carriageTypeService;


    @PostMapping(ApiPaths.ADMIN_API + "/createCarriageType")
    public ResponseEntity<CarriageTypeDTO> createCarriageType(@RequestBody CarriageTypeDTO carriageTypeDTO) {
        try {
            return ResponseEntity.ok(carriageTypeService.createCarriageType(carriageTypeDTO));
        } catch (WrongDTOException e) {
            return ResponseEntity.badRequest().body(carriageTypeDTO);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(carriageTypeDTO);
        }
    }

    @PostMapping(ApiPaths.ADMIN_API + "/updateCarriageType")
    public ResponseEntity<CarriageTypeDTO> updateCarriageType(
            @RequestBody CarriageTypeDTO carriageTypeDTO
    ) {
        try {
            return ResponseEntity.ok(carriageTypeService.updateCarriageType(carriageTypeDTO));
        } catch (CarriageTypeDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(carriageTypeDTO);
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/deleteCarriageType")
    public ResponseEntity<CarriageTypeDTO> deleteCarriageType(
            @RequestParam(name = "id") String id
    ) {
        try {
            Long idL = Long.parseLong(id);
            if (idL == null) {
                return ResponseEntity.unprocessableEntity().body(null);
            }
            return ResponseEntity.ok(carriageTypeService.deleteCarriageType(idL));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        } catch (CarriageTypeDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
           return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getCarriageTypeByName")
    public ResponseEntity<CarriageTypeDTO> getCarriageTypeByName(
            @RequestParam(name = "name") String name
    ) {
        try {
            return ResponseEntity.ok(carriageTypeService.getCarriageTypeByName(name));
        } catch (CarriageTypeDoesntExist e) {
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getAllCarriageType")
    public ResponseEntity<List<CarriageTypeDTO>> getCarriageAllCarriageTypes() {
        try {
            return  ResponseEntity.ok(carriageTypeService.getAllCarriageTypes());
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getAllCarriageByType")
    public ResponseEntity<List<CarriageDTO>> getCarriageByType(
            @RequestParam(name = "typeName") String CarriageType
    ) {
        try {
            return ResponseEntity.ok(carriageTypeService.getCarriagesByType(Long.parseLong(CarriageType)));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (CarriageTypeDoesntExist e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }


}
