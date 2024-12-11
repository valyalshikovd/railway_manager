package com.example.railway_manager.controller;


import com.example.railway_manager.dto.railways.LocomotiveDTO;
import com.example.railway_manager.exception.LocomotiveDoesntExist;
import com.example.railway_manager.exception.LocomotiveTypeDoesntExist;
import com.example.railway_manager.exception.TrainDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.service.railway.LocomotiveService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocomotiveController {

    private final LocomotiveService locomotiveService;

    @PostMapping(ApiPaths.ADMIN_API + "/createLocomotive")
    public ResponseEntity<LocomotiveDTO> createLocomotive(@RequestBody LocomotiveDTO locomotiveDTO) {
        try {
            locomotiveService.createLocomotive(locomotiveDTO);
            return ResponseEntity.ok(locomotiveDTO);
        }catch (WrongDTOException e){
            return ResponseEntity.unprocessableEntity().build();
        }catch (LocomotiveDoesntExist e){
            return ResponseEntity.unprocessableEntity().body(
                    LocomotiveDTO
                    .builder()
                            .locomotive_state(null)
                            .dateOfPurchase(null)
                            .trainDTO(null)
                            .id(null)
                    .build());
        } catch (LocomotiveTypeDoesntExist e) {
            return ResponseEntity.notFound().build();
        }catch(TrainDoesntExist e){
            return ResponseEntity.unprocessableEntity().body(
                    LocomotiveDTO
                            .builder()
                            .locomotive_state(null)
                            .dateOfPurchase(null)
                            .locomotiveTypeDto(null)
                            .id(null)
                            .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(locomotiveDTO);
        }
    }

    @PostMapping(ApiPaths.ADMIN_API + "/updateLocomotive")
    public ResponseEntity<LocomotiveDTO> updateLocomotive(@RequestBody LocomotiveDTO locomotiveDTO) {
        try {
            locomotiveService.updateLocomotive(locomotiveDTO);
            return ResponseEntity.ok(locomotiveDTO);
        }catch (TrainDoesntExist e){
            return ResponseEntity.unprocessableEntity().body(
                    LocomotiveDTO
                            .builder()
                            .locomotive_state(null)
                            .dateOfPurchase(null)
                            .locomotiveTypeDto(null)
                            .id(null)
                            .build());
        }catch (LocomotiveDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(locomotiveDTO);
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/deleteLocomotive")
    public ResponseEntity<LocomotiveDTO> delete(@RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(locomotiveService.deleteLocomotive(id));
        }catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        }catch (LocomotiveDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getAllLocomotives")
    public ResponseEntity<List<LocomotiveDTO>> getAllLocomotives() {
        try {
            return ResponseEntity.ok(locomotiveService.getAllLocomotives());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.BASE_API + "/getLocomotiveById")
    public ResponseEntity<LocomotiveDTO> getLocomotiveById(@RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(locomotiveService.getLocomotivesById(id));
        }catch (LocomotiveDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
