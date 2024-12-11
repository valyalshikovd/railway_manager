package com.example.railway_manager.mapper;

import com.example.railway_manager.dto.railways.CarriageDTO;
import com.example.railway_manager.dto.railways.CarriageTypeDTO;
import com.example.railway_manager.model.CarriageType;

public class CarriageTypeMapper {

    public static CarriageTypeDTO toDTO(CarriageType carriageType) {
        return CarriageTypeDTO
                .builder()
                .id(carriageType.getId())
                .capacity(carriageType.getCapacity())
                .name(carriageType.getNameType())
                .build();
    }
}
