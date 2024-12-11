package com.example.railway_manager.mapper;

import com.example.railway_manager.dto.railways.LocomotiveDTO;
import com.example.railway_manager.dto.railways.LocomotiveTypeDTO;
import com.example.railway_manager.dto.railways.TrainDTO;
import com.example.railway_manager.model.Locomotive;

public class LocomotiveMapper {

    public static LocomotiveDTO toDto(Locomotive locomotive) {
        return  LocomotiveDTO
                .builder()
                .id(locomotive.getId())
                .locomotive_state(locomotive.getLocomotive_state())
                .dateOfPurchase(locomotive.getDateOfPurchase())
                .countOfEngineHours(locomotive.getCountOfEngineHours())
                .locomotiveTypeDto(
                        LocomotiveTypeMapper.mapToDto(locomotive.getLocomotiveType()))
                .trainDTO((locomotive.getTrain() == null ? null :  TrainMapper.toDTO(locomotive.getTrain())))
                .build();
    }
}
