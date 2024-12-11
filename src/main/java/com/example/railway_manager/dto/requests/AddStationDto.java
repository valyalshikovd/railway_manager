package com.example.railway_manager.dto.requests;

import com.example.railway_manager.dto.railways.StationDTO;
import com.example.railway_manager.model.Station;
import lombok.Data;

@Data
public class AddStationDto {
    private Long segmentId;
    private Long stationId;
}
