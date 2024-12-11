package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.*;
import com.example.railway_manager.repository.railway.LinkStationService;
import com.example.railway_manager.service.railway.LineService;
import com.example.railway_manager.service.railway.SegmentService;
import com.example.railway_manager.service.railway.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LinkStationServiceImpl implements LinkStationService {

    private final StationService stationService;
    private final SegmentService segmentService;
    private final LineService lineService;

    @Override
    public SegmentDTO linkStations(ConnectionDTO connectionDTO) {

        List<SegmentDTO> segmentFirstDTOList = stationService.getFirstSegments(connectionDTO.getToId());
        List<SegmentDTO> segmentSecondDTOList = stationService.getSecondSegments(connectionDTO.getToId());

        SegmentDTO segmentDTO = null;

        for (SegmentDTO segmentDTOcurr : segmentFirstDTOList) {

            if(Objects.equals(connectionDTO.getFromId(), segmentService.getSecondStation(segmentDTOcurr.getId()).getId())){
                 segmentDTO = segmentDTOcurr;
            };
        }

        for (SegmentDTO segmentDTOcurr : segmentSecondDTOList) {

            if(Objects.equals(connectionDTO.getFromId(), segmentService.getFirstStation(segmentDTO.getId()).getId())){
                segmentDTO = segmentDTOcurr;
            };
        }

        if(segmentDTO != null){
            segmentDTO.setComplexity(connectionDTO.getDistance());
            return segmentService.update(segmentDTO);
        }

        segmentDTO = segmentService.create(SegmentDTO
                .builder()
                .level(1)
                .complexity(connectionDTO.getDistance())
                .build());

        segmentService.addFirstStation(segmentDTO.getId(), connectionDTO.getToId());
        segmentService.addSecondStation(segmentDTO.getId(), connectionDTO.getFromId());

        return segmentDTO;
    }

    @Override
    public LineDTO linkStationsInLine(LineCreatingDto lineCreatingDto) {

        LineDTO lineDTO = LineDTO.builder().name(lineCreatingDto.getLineName()).build();

        lineDTO.setSegments(
                lineCreatingDto.getList().stream().map(
                        this::linkStations
                ).toList()
        );

        List<StationDTO> list = new ArrayList<>(lineCreatingDto.getList().stream().map(
                ConnectionDTO::getFromId
        ).map(stationService::getById).toList());

        list.add(stationService.getById(
                                        lineCreatingDto
                                                .getList()
                                                .getLast()
                                                .getToId()));
        lineDTO.setStations(list);

        return lineService.createLine(lineDTO);
    }
}
