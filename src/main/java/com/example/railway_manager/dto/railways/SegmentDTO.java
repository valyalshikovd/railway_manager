package com.example.railway_manager.dto.railways;

import com.example.railway_manager.exception.WrongDTOException;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class SegmentDTO {
    private Long id;

//    private StationDTO stationOne;
//    private StationDTO stationTwo;

    private Integer complexity;
    private Integer level;

    private List<LineDTO> lines;


    public void validateDTO(){
       if(complexity == null){
           throw new WrongDTOException("complexity is null");
       }
       if(level == null){
           throw new WrongDTOException("level is null");
       }
    }

}
