package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.ConnectionDTO;
import com.example.railway_manager.model.Segment;
import com.example.railway_manager.service.railway.SegmentService;
import com.example.railway_manager.service.railway.ValidateConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidateConnectionsServiceImpl implements ValidateConnectionService {

    private final SegmentService segmentService;


    @Override
    public List<ConnectionDTO> validate(List<ConnectionDTO> connections) {

        return connections.stream().peek(
                (connectionDTO ) -> {
                    Segment segment = segmentService.checkSegmentAmongTwoStations(connectionDTO.getToId(), connectionDTO.getFromId());
                    if(segment != null) {
                        connectionDTO.setDistance(segment.getComplexity());
                    }
                }
        ).toList();

    }
}
