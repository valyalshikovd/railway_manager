package com.example.railway_manager.controller;

import com.example.railway_manager.dto.railways.ConnectionDTO;
import com.example.railway_manager.dto.railways.LineCreatingDto;
import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.exception.StationDoesntExist;
import com.example.railway_manager.repository.railway.LinkStationService;
import com.example.railway_manager.service.railway.ValidateConnectionService;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinkStationController {


    private final LinkStationService linkStationService;
    private final ValidateConnectionService validateConnectionService;

    @PostMapping(ApiPaths.ADMIN_API + "/linkStations")
    public ResponseEntity<LineDTO> linkStation(@RequestBody LineCreatingDto lineCreatingDto) {

        try {
            System.out.println(lineCreatingDto);
            return ResponseEntity.ok(linkStationService.linkStationsInLine(lineCreatingDto));
        } catch (Exception e) {
            throw e;
        }
    }



    @PostMapping(ApiPaths.ADMIN_API + "/validateLinks")
    public ResponseEntity<List<ConnectionDTO>> linkStation(@RequestBody List<ConnectionDTO> connections) {

        try {
            System.out.println(connections);
            return ResponseEntity.ok(validateConnectionService.validate(connections));
        }
        catch (StationDoesntExist e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            throw e;
        }
    }
}
