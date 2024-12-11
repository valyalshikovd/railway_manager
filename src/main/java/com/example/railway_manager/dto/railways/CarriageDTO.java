package com.example.railway_manager.dto.railways;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
@Builder
@Data
public class CarriageDTO {
    private Long id;
    private Long carriage_state;
    private LocalDate dateOfPurchase;
    private Long countOfEngineHours;
    private CarriageTypeDTO type;
    private TrainDTO trainDto;
}
