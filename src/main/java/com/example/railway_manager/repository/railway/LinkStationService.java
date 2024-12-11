package com.example.railway_manager.repository.railway;

import com.example.railway_manager.dto.railways.*;

import java.util.List;

public interface LinkStationService {
    SegmentDTO linkStations(ConnectionDTO connectionDTO);

    LineDTO linkStationsInLine(LineCreatingDto lineCreatingDto);
}
