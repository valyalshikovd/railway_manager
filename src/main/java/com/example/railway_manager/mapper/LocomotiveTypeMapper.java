package com.example.railway_manager.mapper;

import com.example.railway_manager.dto.railways.LocomotiveTypeDTO;
import com.example.railway_manager.model.LocomotiveType;

public class LocomotiveTypeMapper {

    public static LocomotiveTypeDTO mapToDto( LocomotiveType locomotive ) {
        return LocomotiveTypeDTO
                .builder()
                .id(locomotive.getId())
                .power(locomotive.getPower())
                .name(locomotive.getName())
                .build();
    }
}
