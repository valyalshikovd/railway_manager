package com.example.railway_manager.dto.railways;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class CarriageTypeDTO{
    private Long id;
    private Long capacity;
    private String name;
    private List<CarriageDTO> carriages;
}