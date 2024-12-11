package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.CarriageDTO;
import com.example.railway_manager.dto.railways.TrainDTO;
import com.example.railway_manager.exception.CarriageDoesntExist;
import com.example.railway_manager.exception.CarriageTypeDoesntExist;
import com.example.railway_manager.exception.TrainDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.mapper.CarriageMapper;
import com.example.railway_manager.model.Carriage;
import com.example.railway_manager.model.CarriageType;
import com.example.railway_manager.model.Train;
import com.example.railway_manager.repository.railway.CarriageRepository;
import com.example.railway_manager.repository.railway.CarriageTypeRepository;
import com.example.railway_manager.repository.railway.TrainRepository;
import com.example.railway_manager.service.railway.CarriageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarriageServiceImpl implements CarriageService {
    private final CarriageRepository CarriageRepository;
    private final CarriageTypeRepository CarriageTypeRepository;
    private final TrainRepository trainRepository;


    @Override
    @Transactional
    public void createCarriage(CarriageDTO carriage) {
        Carriage entity = new Carriage();

        Long status = carriage.getCarriage_state();
        if(status == null){
            throw new WrongDTOException("Status is null");
        }
        entity.setCarriage_state(status);

        LocalDate date = carriage.getDateOfPurchase();
        if(date == null){
            throw new WrongDTOException("Date is null");
        }
        entity.setDateOfPurchase(date);

        Long countOfEngineHours = carriage.getCountOfEngineHours();
        if(countOfEngineHours == null){
            throw new WrongDTOException("countOfEngineHours is null");
        }
        entity.setCountOfEngineHours(countOfEngineHours);

        CarriageType CarriageTypeEntity = CarriageTypeRepository
                .findByNameType(carriage.getType().getName());

        if(CarriageTypeEntity == null){
            throw new CarriageTypeDoesntExist(carriage
                    .getType()
                    .getName());
        }
        entity.setCarriageType(CarriageTypeEntity);

        TrainDTO trainDTO = carriage.getTrainDto();
        if(trainDTO != null){

            Train trainEntity;
            try{
                trainEntity = trainRepository.getReferenceById(trainDTO.getId());
            }catch (Exception e){
                throw new TrainDoesntExist(trainDTO.getId().toString());
            }

            entity.setTrain(trainEntity);

        }

        CarriageRepository.save(entity);
    }

    @Override
    public void updateCarriage(CarriageDTO carriage) {
        Carriage entity = getById(carriage.getId());
        Long status = carriage.getCarriage_state();
        if(status != null){
            entity.setCarriage_state(status);
        }

        LocalDate date = carriage.getDateOfPurchase();
        if(date != null){
            entity.setDateOfPurchase(date);
        }
        Long countOfEngineHours = carriage.getCountOfEngineHours();
        if(countOfEngineHours != null){
            entity.setCountOfEngineHours(countOfEngineHours);
        }

        if(carriage.getType() != null){
            CarriageType CarriageTypeEntity = CarriageTypeRepository
                    .findByNameType(carriage
                            .getType()
                            .getName());

            if(CarriageTypeEntity != null){
                entity.setCarriageType(CarriageTypeEntity);
            }
        }

        TrainDTO trainDTO = carriage.getTrainDto();
        if(trainDTO != null){

            Train trainEntity;
            try{
                trainEntity = trainRepository.getReferenceById(trainDTO.getId());
            }catch (Exception e){
                throw new TrainDoesntExist(trainDTO.getId().toString());
            }

            entity.setTrain(trainEntity);

        }
        CarriageRepository.save(entity);

    }

    @Override
    public CarriageDTO deleteCarriage(Long id) {
        Carriage entity = getById(id);
        CarriageRepository.delete(entity);
        return CarriageDTO
                .builder()
                .id(entity.getId())
                .carriage_state(entity.getCarriage_state())
                .dateOfPurchase(entity.getDateOfPurchase())
                .countOfEngineHours(entity.getCountOfEngineHours())
                .build();
    }

    @Override
    @Transactional
    public List<CarriageDTO> getAllCarriages() {


        List<CarriageDTO> CarriageDTOS = CarriageRepository
                .findAll()
                .stream().
                map(CarriageMapper::toDto)
                .toList();


        return CarriageDTOS;
    }

    @Override
    @Transactional
    public CarriageDTO getCarriagesById(Long id) {
        Carriage entity = getById(id);

        return CarriageMapper.toDto(entity);
    }


    private Carriage getById(Long id){
        Optional<Carriage> carriage = CarriageRepository.findById(id);
        if(carriage.isEmpty()){
            throw new CarriageDoesntExist(id.toString());
        }
        return carriage.get();
    }
}
