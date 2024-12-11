package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.CarriageDTO;
import com.example.railway_manager.dto.railways.CarriageTypeDTO;

import java.util.List;

public interface CarriageTypeService {
    CarriageTypeDTO createCarriageType(CarriageTypeDTO CarriageTypeDTO);
    CarriageTypeDTO updateCarriageType(CarriageTypeDTO CarriageTypeDTO);
    CarriageTypeDTO deleteCarriageType(Long id);
    CarriageTypeDTO getCarriageType(Long id);
    CarriageTypeDTO getCarriageTypeByName(String name);
    List<CarriageTypeDTO> getAllCarriageTypes();
    List<CarriageDTO> getCarriagesByType(Long id);
}
