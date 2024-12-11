package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.CarriageDTO;
import com.example.railway_manager.dto.railways.LocomotiveDTO;
import com.example.railway_manager.dto.railways.TrainDTO;
import com.example.railway_manager.dto.railways.TravelDto;
import com.example.railway_manager.exception.CarriageDoesntExist;
import com.example.railway_manager.exception.LocomotiveDoesntExist;
import com.example.railway_manager.exception.TrainDoesntExist;
import com.example.railway_manager.mapper.*;
import com.example.railway_manager.model.Carriage;
import com.example.railway_manager.model.Locomotive;
import com.example.railway_manager.model.Train;
import com.example.railway_manager.repository.railway.CarriageRepository;
import com.example.railway_manager.repository.railway.LocomotiveRepository;
import com.example.railway_manager.repository.railway.TrainRepository;
import com.example.railway_manager.service.railway.TrainService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final CarriageRepository carriageRepository;
    private final LocomotiveRepository locomotiveRepository;

    @Override
    public TrainDTO createTrain(TrainDTO train) {

        System.out.println(train);
        Train trainEntity = new Train();
        trainEntity = trainRepository.save(trainEntity);

        Train finalTrainEntity = trainEntity;
        train
                .getCarriages()
                .forEach((carriageDTO) -> {
                    Optional<Carriage> optionalCarriage = carriageRepository
                            .findById(carriageDTO.getId());
                    if (optionalCarriage.isEmpty()) {
                        throw new CarriageDoesntExist("Carriage don't exist");
                    }
                    Carriage carriage = optionalCarriage.get();
                    carriage.setTrain(finalTrainEntity);

                    carriageRepository.save(carriage);
                });

        Optional<Locomotive> optionalLocomotive = locomotiveRepository
                .findById(train
                        .getLocomotiveDTO()
                        .getId());
        if (optionalLocomotive.isEmpty()) {
            throw new TrainDoesntExist("Locomotive don't exist");
        }
        Locomotive locomotive = optionalLocomotive.get();
        trainEntity.setLocomotive(locomotive);

        System.out.println(trainEntity);

        return TrainMapper.toDTO(trainRepository.save(trainEntity));

    }

    @Override
    public TrainDTO getTrainById(Long id) {
        Optional<Train> trainOptional = trainRepository.findById(id);
        if (trainOptional.isEmpty()) {
            throw new TrainDoesntExist("Train don't exist");
        }
        return TrainMapper.toDTO(trainOptional.get());
    }

    @Override
    public List<TrainDTO> getAllTrains() {
        return trainRepository
                .findAll()
                .stream()
                .map(TrainMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TrainDTO deleteTrain(Long id) {
        Optional<Train> trainOptional = trainRepository.findById(id);
        if (trainOptional.isEmpty()) {
            throw new TrainDoesntExist("Train don't exist");
        }
        Train train = trainOptional.get();
        trainRepository.delete(train);
        return TrainMapper.toDTO(train);
    }

    @Override
    @Transactional
    public List<CarriageDTO> getCarriagesByTrainId(Long id) {

        Optional<Train> trainOptional = trainRepository.findById(id);
        if (trainOptional.isEmpty()) {
            throw new TrainDoesntExist("Train don't exist");
        }

        return trainOptional
                .get()
                .getCarriages()
                .stream()
                .map(
                        carriage -> {
                            return CarriageDTO
                                    .builder()
                                    .id(carriage.getId())
                                    .type(CarriageTypeMapper.toDTO(carriage.getCarriageType()))
                                    .carriage_state(carriage.getCarriage_state())
                                    .countOfEngineHours(carriage.getCountOfEngineHours())
                                    .dateOfPurchase(carriage.getDateOfPurchase())
                                    .build();
                        }
                )
                .toList();
    }

    @Override
    public LocomotiveDTO getLocomotiveByTrainId(Long id) {

        Optional<Train> trainOptional = trainRepository.findById(id);
        if (trainOptional.isEmpty()) {
            throw new TrainDoesntExist("Train don't exist");
        }
        return LocomotiveMapper.toDto(trainOptional.get().getLocomotive());
    }

    @Override
    public TrainDTO updateTrain(TrainDTO train) {

        Optional<Train> trainOptional = trainRepository.findById(train.getId());
        if (trainOptional.isEmpty()) {
            throw new TrainDoesntExist("Train don't exist");
        }
        Train trainEntity = trainOptional.get();
        trainEntity = trainRepository.save(trainEntity);
        trainEntity
                .setCarriages(
                        train
                                .getCarriages()
                                .stream()
                                .map(
                                        carriageDTO -> {
                                            Optional<Carriage> carriageOptional = carriageRepository.findById(carriageDTO.getId());
                                            if (carriageOptional.isEmpty()) {
                                                throw new CarriageDoesntExist("Carriage don't exist");
                                            }
                                            return carriageOptional.get();
                                        }).collect(Collectors.toSet()));

        Optional<Locomotive> locomotive = locomotiveRepository.findById(trainEntity.getLocomotive().getId());
        if (locomotive.isEmpty()) {
            throw new LocomotiveDoesntExist("Locomotive don't exist");
        }
        trainEntity.setLocomotive(locomotive.get());
        return TrainMapper.toDTO(trainRepository.save(trainEntity));
    }

    @Override
    @Transactional
    public List<TravelDto> getTravelsByTrainId(Long id) {

        Optional<Train> trainOptional = trainRepository.findById(id);
        if (trainOptional.isEmpty()) {
            throw new TrainDoesntExist("Train don't exist");
        }
        Train train = trainOptional.get();

        return train.getTravels().stream().map(TravelMapper::toDto).toList();

    }
}
