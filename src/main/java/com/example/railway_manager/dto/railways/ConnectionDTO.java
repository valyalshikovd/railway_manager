package com.example.railway_manager.dto.railways;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectionDTO {

    private String from;
    private String to;
    private Long fromId;
    private Long toId;
    private Integer distance;
}
