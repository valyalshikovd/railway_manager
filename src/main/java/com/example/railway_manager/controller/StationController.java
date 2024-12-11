package com.example.railway_manager.controller;


import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.dto.railways.StationDTO;
import com.example.railway_manager.exception.StationDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.service.railway.StationService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @PostMapping(ApiPaths.ADMIN_API + "/createStation")
    public ResponseEntity<StationDTO> createStation(
            @RequestBody StationDTO stationDTO
    ) {
        try {
            return ResponseEntity.ok(stationService.create(stationDTO));
        } catch (WrongDTOException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(ApiPaths.ADMIN_API + "/updateStation")
    public ResponseEntity<StationDTO> updateStation(
            @RequestBody StationDTO stationDTO
    ) {
        try {
            return ResponseEntity.ok(stationService.update(stationDTO));
        } catch (StationDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (WrongDTOException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/deleteStation")
    public ResponseEntity<StationDTO> deleteStation(
            @RequestParam(name = "id") Long id
    ) {
        try {
            return ResponseEntity.ok(stationService.delete(id));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        } catch (StationDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/findStationById")
    public ResponseEntity<StationDTO> getStation(@RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(stationService.getById(id));
        }catch (StationDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(ApiPaths.ADMIN_API + "/getAllStations")
    public ResponseEntity<List<StationDTO>> getAllStations(){
        try {
            return ResponseEntity.ok(stationService.getAll());
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getFirstSegmentByStation")
    public ResponseEntity<List<SegmentDTO>> getFirstSegments(
            @RequestParam(name = "id") Long id
    ){
        try{
            return ResponseEntity.ok(stationService.getFirstSegments(id));
        }catch (StationDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getSecondSegmentByStation")
    public ResponseEntity<List<SegmentDTO>> getSecondSegments(
            @RequestParam(name = "id") Long id
    ){
        try{
            return ResponseEntity.ok(stationService.getSecondSegments(id));
        }catch (StationDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getLinesByStation")
    public ResponseEntity<List<LineDTO>> getLinesByStation(
            @RequestParam(name = "id") Long id
    ){
        try {
            return ResponseEntity.ok(stationService.getLines(id));
        } catch (StationDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
