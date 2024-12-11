package com.example.railway_manager.dto.railways;


import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class LineDTO {

    private Long id;
    private String name;
    private Double summary_weight;
    private List<SegmentDTO> segments;
    private List<StationDTO> stations;
}
