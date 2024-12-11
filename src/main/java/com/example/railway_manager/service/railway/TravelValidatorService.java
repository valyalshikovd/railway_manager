package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.ValidateReportDto;
import com.example.railway_manager.model.Travel;

public interface TravelValidatorService {

    ValidateReportDto validateTravel(Travel travel);


}
