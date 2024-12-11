package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.CarriageDTO;
import com.example.railway_manager.dto.railways.CarriageTypeDTO;
import com.example.railway_manager.exception.CarriageTypeDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.mapper.CarriageMapper;
import com.example.railway_manager.mapper.CarriageTypeMapper;
import com.example.railway_manager.model.CarriageType;
import com.example.railway_manager.repository.railway.CarriageTypeRepository;
import com.example.railway_manager.service.railway.CarriageTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarriageTypeServiceImpl implements CarriageTypeService {
    private final CarriageTypeRepository carriageTypeRepository;

    @Override
    public CarriageTypeDTO createCarriageType(CarriageTypeDTO CarriageTypeDTO) {
        CarriageType entity = new CarriageType();

        Long capacity = CarriageTypeDTO.getCapacity();
        if (capacity == null) {
            throw new WrongDTOException("capacity is not allowed");
        }
        entity.setCapacity(capacity);

        String name = CarriageTypeDTO.getName();
        if (name == null) {
            throw new WrongDTOException("Name is not allowed");
        }
        entity.setNameType(name);

        carriageTypeRepository.save(entity);
        return CarriageTypeDTO;
    }

    @Override
    public CarriageTypeDTO updateCarriageType(CarriageTypeDTO CarriageTypeDTO) {
        CarriageType entity = getById(CarriageTypeDTO.getId());

        Long capacity = CarriageTypeDTO.getCapacity();
        if (capacity != null) {
            entity.setCapacity(capacity);
        }

        String name = CarriageTypeDTO.getName();
        if (name != null) {
            entity.setNameType(name);
        }

        carriageTypeRepository.save(entity);
        return CarriageTypeDTO;
    }

    @Override
    public CarriageTypeDTO deleteCarriageType(Long id) {
        CarriageType entity = getById(id);
        carriageTypeRepository.delete(entity);
        return CarriageTypeMapper.toDTO(entity);
    }

    @Override
    public CarriageTypeDTO getCarriageType(Long id) {
        try {
            return CarriageTypeMapper.toDTO(getById(id));
        }catch (Exception e) {
            throw new CarriageTypeDoesntExist(id.toString());
        }
    }

    @Override
    public CarriageTypeDTO getCarriageTypeByName(String name) {
        try {
            return CarriageTypeMapper.toDTO(carriageTypeRepository.findByNameType(name));
        }catch (Exception e) {
            throw new CarriageTypeDoesntExist(name);
        }
    }

    @Override
    public List<CarriageTypeDTO> getAllCarriageTypes() {
        return carriageTypeRepository.findAll().stream().map(CarriageTypeMapper::toDTO).toList();
    }

    @Override
    public List<CarriageDTO> getCarriagesByType(Long id) {
        CarriageType entity = getById(id);
        return entity.getCarriages().stream().map(CarriageMapper::toDto).toList();
    }

    private CarriageType getById(Long id) {
        Optional<CarriageType> opt = carriageTypeRepository.findById(id);
        if(opt.isEmpty()){
            throw new CarriageTypeDoesntExist(id.toString());
        }
        return opt.get();
    }
}

