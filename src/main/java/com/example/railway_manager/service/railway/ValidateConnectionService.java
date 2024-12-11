package com.example.railway_manager.service.railway;

import com.example.railway_manager.dto.railways.ConnectionDTO;

import java.util.List;

public interface ValidateConnectionService {

    List<ConnectionDTO> validate(List<ConnectionDTO> connections);

}
