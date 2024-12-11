package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.LocomotiveDTO;

import java.util.List;

public interface LocomotiveService {

    void createLocomotive(LocomotiveDTO locomotive);

    void updateLocomotive(LocomotiveDTO locomotive);

    LocomotiveDTO deleteLocomotive(Long id);

    List<LocomotiveDTO> getAllLocomotives();
    LocomotiveDTO getLocomotivesById(Long id);
}
