package com.example.railway_manager.dto.railways;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LineCreatingDto {

    private List<ConnectionDTO> list;
    private String lineName;
}
