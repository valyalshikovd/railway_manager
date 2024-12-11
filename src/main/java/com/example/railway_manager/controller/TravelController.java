package com.example.railway_manager.controller;

import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.TrainDTO;
import com.example.railway_manager.dto.railways.TravelDto;
import com.example.railway_manager.exception.TravelDoesntExist;
import com.example.railway_manager.service.railway.TravelService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

//        travelService.deleteTravelById();
//        travelService.getAllTravels();

    @PostMapping(ApiPaths.ADMIN_API + "/createTravel")
    public ResponseEntity<TravelDto> createTravel(
            @RequestBody  TravelDto travelDto) {
        try {
            return ResponseEntity.ok(travelService.createTravel(travelDto));
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(ApiPaths.ADMIN_API + "/updateTravel")
    public ResponseEntity<TravelDto> updateTravel(
            @RequestBody TravelDto travelDto) {
        try {
            return ResponseEntity.ok(travelService.updateTravel(travelDto));
        }catch (TravelDoesntExist e){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getTravelById")
    public ResponseEntity<TravelDto> getTravelById(@RequestParam(name="id") Long id) {
        try {
            return ResponseEntity.ok(travelService.getTravelById(id));
        }catch (TravelDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/deleteTravelById")
    public ResponseEntity<TravelDto> deleteTravelById(@RequestParam(name="id") Long id) {
        try {
            return ResponseEntity.ok(travelService.deleteTravelById(id));
        }catch (TravelDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.BASE_API + "/getAllTravels")
    public ResponseEntity<List<TravelDto>> getAllTravels() {
        try {
            return ResponseEntity.ok(travelService.getAllTravels());
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.BASE_API + "/getLineByTravelId")
    public ResponseEntity<LineDTO> getLineByTravelId(@RequestParam(name="id") Long id) {
        try {
            return ResponseEntity.ok(travelService.getLineByTravelId(id));
        }catch (TravelDoesntExist e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.BASE_API + "/getTrainByTravelId")
    public ResponseEntity<TrainDTO> getTrainByTravelId(@RequestParam(name="id") Long id) {
        try {
            return ResponseEntity.ok(travelService.getTrainByTravelId(id));
        }catch (TravelDoesntExist e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
