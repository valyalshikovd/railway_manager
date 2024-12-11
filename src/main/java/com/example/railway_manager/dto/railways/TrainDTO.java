package com.example.railway_manager.dto.railways;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class TrainDTO {
    private Long id;
    private LocomotiveDTO locomotiveDTO;
    private List<CarriageDTO> carriages;
    private List<TravelDto> travels;
}
