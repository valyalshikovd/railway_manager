package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.CarriageDTO;
import com.example.railway_manager.dto.railways.LocomotiveDTO;
import com.example.railway_manager.dto.railways.TrainDTO;
import com.example.railway_manager.dto.railways.TravelDto;

import java.util.List;

public interface TrainService {

    TrainDTO createTrain(TrainDTO train);

    TrainDTO getTrainById(Long id);

    List<TrainDTO> getAllTrains();

    TrainDTO deleteTrain(Long id);

    List<CarriageDTO> getCarriagesByTrainId(Long id);

    LocomotiveDTO getLocomotiveByTrainId(Long id);

    TrainDTO updateTrain(TrainDTO train);

    List<TravelDto> getTravelsByTrainId(Long id);
}
