package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.LocomotiveDTO;
import com.example.railway_manager.dto.railways.LocomotiveTypeDTO;
import com.example.railway_manager.exception.LocomotiveTypeDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.mapper.LocomotiveMapper;
import com.example.railway_manager.mapper.LocomotiveTypeMapper;
import com.example.railway_manager.model.LocomotiveType;
import com.example.railway_manager.repository.railway.LocomotiveTypeRepository;
import com.example.railway_manager.service.railway.LocomotiveTypeService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LocomotiveTypeServiceImpl implements LocomotiveTypeService {

    private final LocomotiveTypeRepository locomotiveTypeRepository;

    @Override
    public LocomotiveTypeDTO createLocomotiveType(LocomotiveTypeDTO locomotiveTypeDTO) {
        LocomotiveType entity = new LocomotiveType();

        Long power = locomotiveTypeDTO.getPower();
        if (power == null) {
            throw new WrongDTOException("Power is not allowed");
        }
        entity.setPower(power);

        String name = locomotiveTypeDTO.getName();
        if (name == null) {
            throw new WrongDTOException("Name is not allowed");
        }
        entity.setName(name);

        locomotiveTypeRepository.save(entity);
        return locomotiveTypeDTO;
    }

    @Override
    public LocomotiveTypeDTO updateLocomotiveType(LocomotiveTypeDTO locomotiveTypeDTO) {
        LocomotiveType entity;
        try {
            entity = locomotiveTypeRepository.findById(locomotiveTypeDTO.getId()).get();
        }catch (Exception e) {
            throw new LocomotiveTypeDoesntExist(locomotiveTypeDTO.getId().toString());
        }

        Long power = locomotiveTypeDTO.getPower();
        if (power != null) {
            entity.setPower(power);
        }

        String name = locomotiveTypeDTO.getName();
        if (name != null) {
            entity.setName(name);
        }

        locomotiveTypeRepository.save(entity);
        return locomotiveTypeDTO;
    }

    @Override
    public LocomotiveTypeDTO deleteLocomotiveType(Long id) {
        LocomotiveType entity;
        try {
            entity = locomotiveTypeRepository.findById(id).get();
        }catch (Exception e) {
            throw new LocomotiveTypeDoesntExist(id.toString());
        }
        locomotiveTypeRepository.delete(entity);
        return LocomotiveTypeMapper.mapToDto(entity);
    }

    @Override
    public LocomotiveTypeDTO getLocomotiveType(Long id) {
        try {
            return LocomotiveTypeMapper.mapToDto(locomotiveTypeRepository.findById(id).get());
        }catch (Exception e) {
            throw new LocomotiveTypeDoesntExist(id.toString());
        }
    }

    @Override
    public LocomotiveTypeDTO getLocomotiveTypeByName(String name) {
        try {
            return LocomotiveTypeMapper.mapToDto(locomotiveTypeRepository.findByName(name));
        }catch (Exception e) {
            throw new LocomotiveTypeDoesntExist(name);
        }
    }

    @Override
    public List<LocomotiveTypeDTO> getAllLocomotiveTypes() {
        return locomotiveTypeRepository.findAll().stream().map(LocomotiveTypeMapper::mapToDto).toList();
    }

    @Override
    public List<LocomotiveDTO> getLocomotivesByType(Long id) {
        LocomotiveType entity;
        try {
            entity = locomotiveTypeRepository.findById(id).get();
        }catch (Exception e) {
            throw new LocomotiveTypeDoesntExist(id.toString());
        }
        return entity.getLocomotives().stream().map(LocomotiveMapper::toDto).toList();
    }
}
