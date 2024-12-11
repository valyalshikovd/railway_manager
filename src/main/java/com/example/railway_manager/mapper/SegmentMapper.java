package com.example.railway_manager.mapper;

import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.model.Segment;

public class SegmentMapper {

    public static SegmentDTO toDto(Segment segment) {

        return SegmentDTO
                .builder()
                .id(segment.getId())
                .complexity(segment.getComplexity())
                .level(segment.getLevel())
                .build();
    }


    public static Segment toDto(SegmentDTO segment) {

        Segment entity = new Segment();
        segment.setId(segment.getId());
        segment.setComplexity(segment.getComplexity());
        segment.setLevel(segment.getLevel());
        return entity;
    }

}
