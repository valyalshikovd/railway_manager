package com.example.railway_manager.dto.railways;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LocomotiveDTO {
    private Long id;
    private Long locomotive_state;
    private LocalDate dateOfPurchase;
    private Long countOfEngineHours;
    private LocomotiveTypeDTO locomotiveTypeDto;
    private TrainDTO trainDTO;
}
