package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.*;
import com.example.railway_manager.exception.LineDoesntExist;
import com.example.railway_manager.exception.TrainDoesntExist;
import com.example.railway_manager.exception.TravelDoesntExist;
import com.example.railway_manager.mapper.LineMapper;
import com.example.railway_manager.mapper.TrainMapper;
import com.example.railway_manager.mapper.TravelMapper;
import com.example.railway_manager.model.Line;
import com.example.railway_manager.model.Train;
import com.example.railway_manager.model.Travel;
import com.example.railway_manager.repository.railway.LineRepository;
import com.example.railway_manager.repository.railway.TrainRepository;
import com.example.railway_manager.repository.railway.TravelRepository;
import com.example.railway_manager.service.railway.TravelService;
import com.example.railway_manager.service.railway.TravelValidatorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TravelServiceImpl implements TravelService {

    private final TrainRepository trainRepository;
    private final LineRepository lineRepository;
    private final TravelRepository travelRepository;
    private final TravelValidatorService travelValidatorService;

    @Override
    public TravelDto createTravel(TravelDto travelDto) {
        Travel travel = new Travel();

        travel.setTravelTime(travelDto.getTravelTime());
        travel.setArrivalTime(travelDto.getArrivalTime());
        travel.setState(travelDto.getState());
        travel.setDateDeparture(travelDto.getDateDeparture());

        Optional<Train> optionalTrain = trainRepository.findById(travelDto.getTrain().getId());
        if(optionalTrain.isEmpty()){
            throw new TrainDoesntExist("Train doesnt exists");
        }
        travel.setTrain(optionalTrain.get());

        Optional<Line> optionalLine = lineRepository.findById(travelDto.getLine().getId());
        if(optionalLine.isEmpty()){
            throw new LineDoesntExist("Line doesnt exists");
        }
        travel.setLine(optionalLine.get());


        if(!travelValidatorService.validateTravel(travel).isValid()){
            throw new TravelDoesntExist("Travel doesnt exists");
        };

        travel = travelRepository.save(travel);
        return TravelMapper.toDto(travel);
    }

    @Override
    public TravelDto getTravelById(Long id) {
        Optional<Travel> optionalTravel = travelRepository.findById(id);
        if(optionalTravel.isEmpty()){
            throw new TravelDoesntExist("Travel doesnt exists");
        }
        return TravelMapper.toDto(optionalTravel.get());
    }

    @Override
    public List<TravelDto> getAllTravels() {
        List<Travel> travels = travelRepository.findAll();
        return travels.stream().map(TravelMapper::toDto).toList();
    }

    @Override
    public TravelDto updateTravel(TravelDto travelDto) {

        Optional<Travel> optionalTravel = travelRepository.findById(travelDto.getId());
        if(optionalTravel.isEmpty()){
            throw new TravelDoesntExist("Travel doesnt exists");
        }
        Travel travel = optionalTravel.get();

        travel.setTravelTime(travelDto.getTravelTime());
        travel.setArrivalTime(travelDto.getArrivalTime());
        travel.setState(travelDto.getState());
        travel.setDateDeparture(travelDto.getDateDeparture());

        Optional<Train> optionalTrain = trainRepository.findById(travelDto.getTrain().getId());
        if(optionalTrain.isEmpty()){
            throw new TrainDoesntExist("Train doesnt exists");
        }
        travel.setTrain(optionalTrain.get());

        Optional<Line> optionalLine = lineRepository.findById(travelDto.getLine().getId());
        if(optionalLine.isEmpty()){
            throw new LineDoesntExist("Line doesnt exists");
        }
        travel.setLine(optionalLine.get());

        travel = travelRepository.save(travel);
        return TravelMapper.toDto(travel);
    }

    @Override
    public TravelDto deleteTravelById(Long id) {
        Optional<Travel> optionalTravel = travelRepository.findById(id);
        if(optionalTravel.isEmpty()){
            throw new TravelDoesntExist("Travel doesnt exists");
        }
        Travel travel = optionalTravel.get();
        travelRepository.delete(travel);
        return TravelMapper.toDto(travel);
    }

    @Override
    @Transactional
    public TrainDTO getTrainByTravelId(Long id) {
        Optional<Travel> optionalTravel = travelRepository.findById(id);
        if(optionalTravel.isEmpty()){
            throw new TravelDoesntExist("Travel doesnt exists");
        }
        Travel travel = optionalTravel.get();

        return TrainMapper.toDTO(travel.getTrain());
    }

    @Override
    @Transactional
    public LineDTO getLineByTravelId(Long id) {
        Optional<Travel> optionalTravel = travelRepository.findById(id);
        if(optionalTravel.isEmpty()){
            throw new TravelDoesntExist("Travel doesnt exists");
        }
        Travel travel = optionalTravel.get();

        return LineMapper.toDto(travel.getLine());
    }
}
