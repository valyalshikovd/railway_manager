package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.dto.railways.StationDTO;
import com.example.railway_manager.model.Line;
import org.springframework.data.util.Pair;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

public interface LineService {

    LineDTO createLine(LineDTO lineDTO);
    LineDTO deleteLine(Long id);
    LineDTO getLineById(Long id);
    LineDTO getLineByName(String name);
    List<LineDTO> getAllLines();
    List<StationDTO> getStationsByLineId(Long id);
    List<SegmentDTO> getSegmentsByLineId(Long id);
    HashMap<Long, Pair<Instant, Instant>> calculateDates(Line line, Instant dateFirst);
}
