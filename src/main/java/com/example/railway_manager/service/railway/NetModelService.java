package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.dto.railways.StationDTO;

import java.util.List;
import java.util.Map;

public interface NetModelService {

    Map<StationDTO, List<SegmentDTO>> getGraph();
    boolean addSegment(SegmentDTO segmentDTO);
    boolean removeSegment(SegmentDTO segmentDTO);
    boolean updateSegment(SegmentDTO segmentDTO);
    boolean addStation(StationDTO stationDTO);
}
