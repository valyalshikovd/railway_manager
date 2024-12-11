package com.example.railway_manager.controller;

import com.example.railway_manager.dto.railways.CarriageDTO;
import com.example.railway_manager.exception.CarriageDoesntExist;
import com.example.railway_manager.exception.TrainDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.service.railway.CarriageService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarriageController {

    private final CarriageService carriageService;

    @PostMapping(ApiPaths.ADMIN_API + "/createCarriage")
    public ResponseEntity<CarriageDTO> createCarriage(@RequestBody CarriageDTO carriageDTO) {
        try {
            carriageService.createCarriage(carriageDTO);
            return ResponseEntity.ok(carriageDTO);
        } catch (WrongDTOException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (CarriageDoesntExist e) {
            return ResponseEntity.unprocessableEntity().body(
                    CarriageDTO
                            .builder()
                            .carriage_state(null)
                            .dateOfPurchase(null)
                            .trainDto(null)
                            .id(null)
                            .build());
        } catch (TrainDoesntExist e) {
            return ResponseEntity.unprocessableEntity().body(
                    CarriageDTO
                            .builder()
                            .carriage_state(null)
                            .dateOfPurchase(null)
                            .type(null)
                            .id(null)
                            .build());
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(carriageDTO);
        }
    }

    @PostMapping(ApiPaths.ADMIN_API + "/updateCarriage")
    public ResponseEntity<CarriageDTO> updateCarriage(@RequestBody CarriageDTO carriageDTO) {
        try {
            carriageService.updateCarriage(carriageDTO);
            return ResponseEntity.ok(carriageDTO);
        } catch (TrainDoesntExist e) {
            return ResponseEntity.unprocessableEntity().body(
                    CarriageDTO
                            .builder()
                            .carriage_state(null)
                            .dateOfPurchase(null)
                            .type(null)
                            .id(null)
                            .build());
        } catch (CarriageDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw e;
            //return ResponseEntity.badRequest().body(carriageDTO);
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/deleteCarriage")
    public ResponseEntity<CarriageDTO> delete(@RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(carriageService.deleteCarriage(id));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        } catch (CarriageDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getAllCarriages")
    public ResponseEntity<List<CarriageDTO>> getAllCarriages() {
        try {
            return ResponseEntity.ok(carriageService.getAllCarriages());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.BASE_API + "/getById")
    public ResponseEntity<CarriageDTO> getCarriageById(@RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(carriageService.getCarriagesById(id));
        } catch (CarriageDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
