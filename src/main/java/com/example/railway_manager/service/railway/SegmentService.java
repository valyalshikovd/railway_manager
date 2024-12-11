package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.dto.railways.StationDTO;
import com.example.railway_manager.model.Segment;

import java.util.List;

public interface SegmentService {

    SegmentDTO create(SegmentDTO segmentDTO);
    SegmentDTO update(SegmentDTO segmentDTO);
    SegmentDTO delete(Long id);
    SegmentDTO findById(Long id);
    List<SegmentDTO> findAll();
    SegmentDTO addFirstStation(Long segmentId, Long stationId);
    SegmentDTO addSecondStation(Long segmentId, Long stationId);
    StationDTO getFirstStation(Long segmentId);
    StationDTO getSecondStation(Long segmentId);
    List<LineDTO> getLines(Long segmentId);
    Segment checkSegmentAmongTwoStations(Long stationId1, Long stationId2);
}
