package com.example.railway_manager.mapper;

import com.example.railway_manager.dto.railways.StationDTO;
import com.example.railway_manager.model.Station;

public class StationMapper {
    public static StationDTO toDto(Station station) {
        return StationDTO
                .builder()
                .id(station.getId())
                .name(station.getName())
                .build();
    }
}
