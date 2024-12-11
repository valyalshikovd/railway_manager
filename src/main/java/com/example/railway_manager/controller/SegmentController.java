package com.example.railway_manager.controller;


import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.dto.railways.StationDTO;
import com.example.railway_manager.dto.requests.AddStationDto;
import com.example.railway_manager.exception.SegmentDoesntExist;
import com.example.railway_manager.exception.StationDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.service.railway.SegmentService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SegmentController {

    private final SegmentService segmentService;

//    List<LineDTO> getLines(Long segmentId);

    @PostMapping(ApiPaths.ADMIN_API + "/createSegment")
    public ResponseEntity<SegmentDTO> createSegment(
            @RequestBody SegmentDTO segmentDTO) {
        try {
            return ResponseEntity.ok(segmentService.create(segmentDTO));
        } catch (WrongDTOException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(ApiPaths.ADMIN_API + "/updateSegment")
    public ResponseEntity<SegmentDTO> updateSegment(
            @RequestBody SegmentDTO segmentDTO) {
        try {
            return ResponseEntity.ok(segmentService.update(segmentDTO));
        } catch (WrongDTOException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (SegmentDoesntExist e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/deleteSegment")
    public ResponseEntity<SegmentDTO> deleteSegment(
            @RequestParam(name = "id")  Long id){
        try{
            return ResponseEntity.ok(segmentService.delete(id));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        } catch (SegmentDoesntExist e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getSegmentById")
    public ResponseEntity<SegmentDTO> getSegmentById(
            @RequestParam(name = "id")  Long id
    ){
        try {
            return ResponseEntity.ok(segmentService.findById(id));
        } catch (SegmentDoesntExist e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getAllSegments")
    public ResponseEntity<List<SegmentDTO>> getAllSegments() {
        try {
            return ResponseEntity.ok(segmentService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(ApiPaths.ADMIN_API + "/addFirstStationToSegment")
    public ResponseEntity<SegmentDTO> addFirstStationToSegment(
            @RequestBody AddStationDto addStationDto
    ) {
       try {
           return ResponseEntity.ok(
                   segmentService.addFirstStation(
                           addStationDto.getSegmentId(),
                           addStationDto.getStationId()
                   )
           );
       }catch (SegmentDoesntExist | StationDoesntExist e){
           return ResponseEntity.notFound().build();
       }catch (Exception e){
           return ResponseEntity.badRequest().build();
       }
    }
    @PostMapping(ApiPaths.ADMIN_API + "/addSecondStationToSegment")
    public ResponseEntity<SegmentDTO> addSecondStationToSegment(
            @RequestBody AddStationDto addStationDto
    ) {
        try {
            return ResponseEntity.ok(
                    segmentService.addSecondStation(
                            addStationDto.getSegmentId(),
                            addStationDto.getStationId()
                    )
            );
        }catch (SegmentDoesntExist | StationDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getFirstStationById")
    public ResponseEntity<StationDTO> getFirstStationById(
            @RequestParam(name = "id")  Long id
    ){
        try {
            return ResponseEntity.ok(segmentService.getFirstStation(id));
        }catch (SegmentDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getSecondStationById")
    public ResponseEntity<StationDTO> getSecondStationById(
            @RequestParam(name = "id")  Long id
    ){
        try {
            return ResponseEntity.ok(segmentService.getSecondStation(id));
        }catch (SegmentDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getLinesBySegmentID")
    public ResponseEntity<List<LineDTO>> getLinesBySegment(
            @RequestParam(name = "id")  Long id
    ){
        try {
            return ResponseEntity.ok(segmentService.getLines(id));
        }catch (SegmentDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
