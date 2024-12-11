package com.example.railway_manager.mapper;

import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.model.Line;

public class LineMapper {
    public static LineDTO toDto(Line line) {
        return LineDTO
                .builder()
                .id(line.getId())
                .name(line.getName())
                .summary_weight(line.getSummary_weight())
                .build();
    }

}
