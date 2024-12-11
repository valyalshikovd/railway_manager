package com.example.railway_manager.controller;


import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.dto.railways.StationDTO;
import com.example.railway_manager.exception.LineDoesntExist;
import com.example.railway_manager.exception.WrongLineException;
import com.example.railway_manager.mapper.LineMapper;
import com.example.railway_manager.service.railway.LineService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LineController {

    private final LineService lineService;


    @PostMapping(ApiPaths.ADMIN_API + "/createLine")
    public ResponseEntity<LineDTO> createLine(
            @RequestBody LineDTO lineDTO) {
        try {
            lineService.createLine(lineDTO);
            return ResponseEntity.ok().body(lineDTO);
        } catch (WrongLineException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(lineDTO);
        }

    }

    @GetMapping(ApiPaths.ADMIN_API + "/getLineById")
    public ResponseEntity<LineDTO> getLineById(@RequestParam(name = "id")  Long id) {
        try{
            return ResponseEntity.ok(lineService.getLineById(id));
        }catch (LineDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getLineByName")
    public ResponseEntity<LineDTO> getLineByName(@RequestParam(name = "name") String name) {
        try{
            return ResponseEntity.ok(lineService.getLineByName(name));
        }catch (LineDoesntExist e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/deleteLineById")
    public ResponseEntity<LineDTO> deleteLine(@RequestParam(name = "id")  Long id) {
        try {
            return ResponseEntity.ok(lineService.deleteLine(id));
        } catch (LineDoesntExist e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/findAllLines")
    public ResponseEntity<List<LineDTO>> getAllLines(){
        try {
            return ResponseEntity.ok(lineService.getAllLines());
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping(ApiPaths.BASE_API + "/getStationByLine")
    public ResponseEntity<List<StationDTO>> getStationsByLine(@RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(lineService.getStationsByLineId(id));
        } catch (LineDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping(ApiPaths.ADMIN_API + "/getSegmentsByLine")
    public ResponseEntity<List<SegmentDTO>> getSegmentsByLine(@RequestParam(name = "id") Long id) {
        try {
            return ResponseEntity.ok(lineService.getSegmentsByLineId(id));
        } catch (LineDoesntExist e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
