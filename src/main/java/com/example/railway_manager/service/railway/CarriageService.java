package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.CarriageDTO;

import java.util.List;

public interface CarriageService {
    void createCarriage(CarriageDTO Carriage);
    void updateCarriage(CarriageDTO Carriage);
    CarriageDTO deleteCarriage(Long id);
    List<CarriageDTO> getAllCarriages();
    CarriageDTO getCarriagesById(Long id);
}
