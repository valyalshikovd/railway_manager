package com.example.railway_manager.dto.railways;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Data
@Builder
public class LocomotiveTypeDTO {

    private Long id;
    private Long power;
    private String name;
    private List<LocomotiveDTO> locomotives;
}
