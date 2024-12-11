package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.ConnectionDTO;
import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.dto.railways.StationDTO;
import com.example.railway_manager.exception.StationDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.mapper.LineMapper;
import com.example.railway_manager.mapper.SegmentMapper;
import com.example.railway_manager.mapper.StationMapper;
import com.example.railway_manager.model.EnrolmentLineStations;
import com.example.railway_manager.model.Station;
import com.example.railway_manager.repository.railway.StationRepository;
import com.example.railway_manager.service.railway.StationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    @Override
    public StationDTO create(StationDTO stationDTO) {
        Station station = new Station();
        if(stationDTO.getName() == null){
            throw new WrongDTOException("name is null");
        }
        station.setName(stationDTO.getName());

        return StationMapper.toDto(stationRepository.save(station));
    }

    @Override
    public StationDTO update(StationDTO stationDTO) {
        Station ent = getEntityById(stationDTO.getId());
        if(stationDTO.getName() == null){
            throw new WrongDTOException("name is null");
        }
        ent.setName(stationDTO.getName());
        return StationMapper.toDto(stationRepository.save(ent));
    }

    @Override
    public StationDTO delete(Long id) {
        Station s = getEntityById(id);
        stationRepository.delete(s);
        return StationMapper.toDto(s);
    }

    @Override
    public StationDTO getById(Long id) {
        return StationMapper.toDto(getEntityById(id));
    }

    @Override
    public List<StationDTO> getAll() {
        return stationRepository
                .findAll()
                .stream()
                .map(StationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<SegmentDTO> getFirstSegments(Long id) {
        Station s = getEntityById(id);
        return s.getSegmentsOne().stream().map(SegmentMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<SegmentDTO> getSecondSegments(Long id) {
        Station s = getEntityById(id);
        return s.getSegmentsTwo().stream().map(SegmentMapper::toDto).toList();
    }

    @Override
    @Transactional
    public List<LineDTO> getLines(Long id) {
        Station s = getEntityById(id);
        return s.getEnrollments()
                .stream()
                .map(EnrolmentLineStations::getLine)
                .map(LineMapper::toDto).toList();
    }


    private Station getEntityById(Long id) {
        Optional<Station> optionalStation = stationRepository.findById(id);
        if(optionalStation.isEmpty()) {
            throw new StationDoesntExist(id.toString());
        }
        return optionalStation.get();
    }
}
