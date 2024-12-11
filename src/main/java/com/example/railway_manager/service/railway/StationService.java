package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.ConnectionDTO;
import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.dto.railways.StationDTO;

import java.util.List;

public interface StationService {
    StationDTO create(StationDTO stationDTO);
    StationDTO update(StationDTO stationDTO);
    StationDTO delete(Long id);
    StationDTO getById(Long id);
    List<StationDTO>  getAll();
    List<SegmentDTO> getFirstSegments(Long id);
    List<SegmentDTO> getSecondSegments(Long id);
    List<LineDTO> getLines(Long id);
}
