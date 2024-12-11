package com.example.railway_manager.mapper;

import com.example.railway_manager.dto.railways.TrainDTO;
import com.example.railway_manager.model.Train;

public class TrainMapper {

    public static TrainDTO toDTO(Train train) {
        return TrainDTO
                .builder()
                .id(train.getId())
                .build();
    }
}
