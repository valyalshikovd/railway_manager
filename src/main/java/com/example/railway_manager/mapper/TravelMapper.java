package com.example.railway_manager.mapper;

import com.example.railway_manager.dto.railways.TravelDto;
import com.example.railway_manager.dto.railways.TravelState;
import com.example.railway_manager.model.Travel;

import java.time.LocalDate;

public class TravelMapper {

    public static TravelDto toDto(Travel travel) {

        return TravelDto
                .builder()
                .id(travel.getId())
                .travelTime(travel.getTravelTime())
                .arrivalTime(travel.getArrivalTime())
                .state(travel.getState())
                .dateDeparture(travel.getDateDeparture())
                .build();

    }
}
