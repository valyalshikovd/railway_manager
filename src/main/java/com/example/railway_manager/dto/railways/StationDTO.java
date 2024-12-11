package com.example.railway_manager.dto.railways;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class StationDTO {

    private Long id;
    private String name;
//    private List<SegmentDTO> segmentsOne;
//    private List<SegmentDTO> segmentsTwo;
    private List<LineDTO> lines;
}
