package com.example.railway_manager.dto.railways;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class TravelDto {

    private Long id;
    private Instant dateDeparture;
    private String state;
    private Instant travelTime;
    private Instant arrivalTime;
    private TrainDTO train;
    private LineDTO line;
}
