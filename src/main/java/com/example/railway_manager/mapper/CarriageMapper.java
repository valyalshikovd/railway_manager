package com.example.railway_manager.mapper;

import com.example.railway_manager.dto.railways.CarriageDTO;
import com.example.railway_manager.model.Carriage;

public class CarriageMapper {
    public static CarriageDTO toDto(Carriage carriage) {
        return CarriageDTO
                .builder()
                .id(carriage.getId())
                .carriage_state(carriage.getCarriage_state())
                .dateOfPurchase(carriage.getDateOfPurchase())
                .countOfEngineHours(carriage.getCountOfEngineHours())
                .type(CarriageTypeMapper.toDTO(carriage.getCarriageType()))
                .build();
    }
}
