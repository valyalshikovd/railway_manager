package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.LocomotiveDTO;
import com.example.railway_manager.dto.railways.LocomotiveTypeDTO;

import java.util.List;

public interface LocomotiveTypeService {

    LocomotiveTypeDTO createLocomotiveType(LocomotiveTypeDTO locomotiveTypeDTO);
    LocomotiveTypeDTO updateLocomotiveType(LocomotiveTypeDTO locomotiveTypeDTO);
    LocomotiveTypeDTO deleteLocomotiveType(Long id);
    LocomotiveTypeDTO getLocomotiveType(Long id);
    LocomotiveTypeDTO getLocomotiveTypeByName(String name);
    List<LocomotiveTypeDTO> getAllLocomotiveTypes();
    List<LocomotiveDTO> getLocomotivesByType(Long id);
}
