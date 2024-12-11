package com.example.railway_manager.controller;

import com.example.railway_manager.dto.railways.CarriageDTO;
import com.example.railway_manager.dto.railways.LocomotiveDTO;
import com.example.railway_manager.dto.railways.TrainDTO;
import com.example.railway_manager.dto.railways.TravelDto;
import com.example.railway_manager.exception.CarriageDoesntExist;
import com.example.railway_manager.exception.LocomotiveDoesntExist;
import com.example.railway_manager.exception.TrainDoesntExist;
import com.example.railway_manager.service.railway.TrainService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TrainController {

    private final TrainService trainService;

    @PostMapping(ApiPaths.ADMIN_API + "/createTrain")
    public ResponseEntity<TrainDTO> createTrain(@RequestBody TrainDTO trainDTO) {
        try {
            return ResponseEntity.ok(trainService.createTrain(trainDTO));
        } catch (CarriageDoesntExist | LocomotiveDoesntExist e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (Exception e) {
            throw e;
            //return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getTrainById")
    public ResponseEntity<TrainDTO> getTrainById(@RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(trainService.getTrainById(id));
        } catch (TrainDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(ApiPaths.ADMIN_API + "/deleteTrainById")
    public ResponseEntity<TrainDTO> deleteTrain(@RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(trainService.deleteTrain(id));
        } catch (TrainDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getAllTrains")
    public ResponseEntity<List<TrainDTO>> getAllTrains() {
        try {
            return ResponseEntity.ok(trainService.getAllTrains());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getCarriagesById")
    public ResponseEntity<List<CarriageDTO>> getCarriagesById(@RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(trainService.getCarriagesByTrainId(id));
        } catch (TrainDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getLocomotiveByTrainId")
    public ResponseEntity<LocomotiveDTO> getLocomotiveByTrainId(
            @RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(trainService.getLocomotiveByTrainId(id));
        } catch (TrainDoesntExist | LocomotiveDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(ApiPaths.ADMIN_API + "/updateTrain")
    public ResponseEntity<TrainDTO> updateTrain(@RequestBody TrainDTO trainDTO) {
        try {
            return ResponseEntity.ok(trainService.updateTrain(trainDTO));
        } catch (TrainDoesntExist | CarriageDoesntExist | LocomotiveDoesntExist e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getTravelsByTrainId")
    public ResponseEntity<List<TravelDto>> getTravelsByTrainId(
            @RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(trainService.getTravelsByTrainId(id));
        } catch (TrainDoesntExist e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
