package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.TrainDTO;
import com.example.railway_manager.dto.railways.TravelDto;

import java.util.List;

public interface TravelService {

    TravelDto createTravel(TravelDto travelDto);

    TravelDto getTravelById(Long id);

    List<TravelDto> getAllTravels();

    TravelDto updateTravel(TravelDto travelDto);

    TravelDto deleteTravelById(Long id);

    TrainDTO getTrainByTravelId(Long id);

    LineDTO getLineByTravelId(Long id);
}
