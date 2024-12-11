package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.LocomotiveDTO;
import com.example.railway_manager.dto.railways.LocomotiveTypeDTO;
import com.example.railway_manager.dto.railways.TrainDTO;
import com.example.railway_manager.exception.LocomotiveDoesntExist;
import com.example.railway_manager.exception.LocomotiveTypeDoesntExist;
import com.example.railway_manager.exception.TrainDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.mapper.LocomotiveMapper;
import com.example.railway_manager.model.Locomotive;
import com.example.railway_manager.model.LocomotiveType;
import com.example.railway_manager.model.Train;
import com.example.railway_manager.repository.railway.LocomotiveRepository;
import com.example.railway_manager.repository.railway.LocomotiveTypeRepository;
import com.example.railway_manager.repository.railway.TrainRepository;
import com.example.railway_manager.service.railway.LocomotiveService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LocomotiveServiceImpl implements LocomotiveService {
    private final LocomotiveRepository locomotiveRepository;
    private final LocomotiveTypeRepository locomotiveTypeRepository;
    private final TrainRepository trainRepository;


    @Override
    @Transactional
    public void createLocomotive(LocomotiveDTO locomotive) {
        Locomotive entity = new Locomotive();

        Long status = locomotive.getLocomotive_state();
        if(status == null){
            throw new WrongDTOException("Status is null");
        }
        entity.setLocomotive_state(status);

        LocalDate date = locomotive.getDateOfPurchase();
        if(date == null){
            throw new WrongDTOException("Date is null");
        }
        entity.setDateOfPurchase(date);

        Long countOfEngineHours = locomotive.getCountOfEngineHours();
        if(countOfEngineHours == null){
            throw new WrongDTOException("countOfEngineHours is null");
        }
        entity.setCountOfEngineHours(countOfEngineHours);

        LocomotiveType locomotiveTypeEntity = locomotiveTypeRepository
                .findByName(locomotive
                        .getLocomotiveTypeDto()
                        .getName());

        if(locomotiveTypeEntity == null){
            throw new LocomotiveTypeDoesntExist(locomotive
                    .getLocomotiveTypeDto()
                    .getName());
        }
        entity.setLocomotiveType(locomotiveTypeEntity);

        TrainDTO trainDTO = locomotive.getTrainDTO();
        if(trainDTO != null){

            Train trainEntity;
            try{
                trainEntity = trainRepository.getReferenceById(trainDTO.getId());
            }catch (Exception e){
                throw new TrainDoesntExist(trainDTO.getId().toString());
            }

            entity.setTrain(trainEntity);

        }

        locomotiveRepository.save(entity);
    }

    @Override
    public void updateLocomotive(LocomotiveDTO locomotive) {
        Locomotive entity = getById(locomotive.getId());

        Long status = locomotive.getLocomotive_state();
        if(status != null){
            entity.setLocomotive_state(status);
        }

        LocalDate date = locomotive.getDateOfPurchase();
        if(date != null){
            entity.setDateOfPurchase(date);
        }
        Long countOfEngineHours = locomotive.getCountOfEngineHours();
        if(countOfEngineHours != null){
            entity.setCountOfEngineHours(countOfEngineHours);
        }

        LocomotiveType locomotiveTypeEntity = locomotiveTypeRepository
                .findByName(locomotive
                        .getLocomotiveTypeDto()
                        .getName());

        if(locomotiveTypeEntity != null){
            entity.setLocomotiveType(locomotiveTypeEntity);
        }

        TrainDTO trainDTO = locomotive.getTrainDTO();
        if(trainDTO != null){

            Train trainEntity;
            try{
                trainEntity = trainRepository.getReferenceById(trainDTO.getId());
            }catch (Exception e){
                throw new TrainDoesntExist(trainDTO.getId().toString());
            }

            entity.setTrain(trainEntity);

        }
        locomotiveRepository.save(entity);

    }

    @Override
    public LocomotiveDTO deleteLocomotive(Long id) {

        Locomotive entity = getById(id);
        locomotiveRepository.delete(entity);
        return LocomotiveDTO
                .builder()
                .id(entity.getId())
                .locomotive_state(entity.getLocomotive_state())
                .dateOfPurchase(entity.getDateOfPurchase())
                .countOfEngineHours(entity.getCountOfEngineHours())
                .build();
    }

    @Override
    @Transactional
    public List<LocomotiveDTO> getAllLocomotives() {


        List<LocomotiveDTO> locomotiveDTOS = locomotiveRepository
                .findAll()
                .stream().
                map(LocomotiveMapper::toDto)
                .toList();


        return locomotiveDTOS;
    }

    @Override
    @Transactional
    public LocomotiveDTO getLocomotivesById(Long id) {
        return LocomotiveMapper.toDto(getById(id));
    }

    private Locomotive getById(Long id){
        Optional<Locomotive> locomotiveOptional = locomotiveRepository.findById(id);
        if(locomotiveOptional.isEmpty()){
            throw new LocomotiveDoesntExist(id.toString());
        }
        return locomotiveOptional.get();
    }

}
