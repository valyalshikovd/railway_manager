package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.dto.railways.StationDTO;
import com.example.railway_manager.exception.SegmentDoesntExist;
import com.example.railway_manager.exception.StationDoesntExist;
import com.example.railway_manager.exception.WrongDTOException;
import com.example.railway_manager.mapper.LineMapper;
import com.example.railway_manager.mapper.SegmentMapper;
import com.example.railway_manager.mapper.StationMapper;
import com.example.railway_manager.model.EnrollmentLineSegment;
import com.example.railway_manager.model.Segment;
import com.example.railway_manager.model.Station;
import com.example.railway_manager.repository.railway.SegmentRepository;
import com.example.railway_manager.repository.railway.StationRepository;
import com.example.railway_manager.service.railway.SegmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SegmentServiceImpl implements SegmentService {

    private final SegmentRepository segmentRepository;
    private final StationRepository stationRepository;

    @Override
    public SegmentDTO create(SegmentDTO segmentDTO) {
        segmentDTO.validateDTO();

        Segment entity = new Segment();
        entity.setComplexity(segmentDTO.getComplexity());
        entity.setLevel(segmentDTO.getLevel());

        Segment resEntity = segmentRepository.save(entity);
        return SegmentMapper.toDto(resEntity);

    }

    @Override
    public SegmentDTO update(SegmentDTO segmentDTO) {
        segmentDTO.validateDTO();
        if(segmentDTO.getId() == null) {
            throw new WrongDTOException("id is null");
        }

        Segment entity = getSegment(segmentDTO.getId());
        entity.setComplexity(segmentDTO.getComplexity());
        entity.setLevel(segmentDTO.getLevel());
        Segment resEntity = segmentRepository.save(entity);
        return SegmentMapper.toDto(resEntity);
    }

    @Override
    public SegmentDTO delete(Long id) {

        Segment entity = getSegment(id);
        segmentRepository.delete(entity);
        return SegmentMapper.toDto(entity);
    }

    @Override
    public SegmentDTO findById(Long id) {
        Segment entity = getSegment(id);
        return SegmentMapper.toDto(entity);
    }

    @Override
    public List<SegmentDTO> findAll() {
        return segmentRepository.findAll().stream().map(SegmentMapper::toDto).toList();
    }

    @Override
    @Transactional
    public SegmentDTO addFirstStation(Long segmentId, Long stationId) {
        Optional<Station> optionalStationEmpty = stationRepository.findById(stationId);
        if(optionalStationEmpty.isEmpty()){
            throw new StationDoesntExist("station is empty");
        }

        Segment entity = getSegment(segmentId);
        Station entityStation = optionalStationEmpty.get();
        entity.setStationOne(entityStation);
        Segment resEntity = segmentRepository.save(entity);
        return SegmentMapper.toDto(resEntity);
    }

    @Override
    @Transactional
    public SegmentDTO addSecondStation(Long segmentId, Long stationId) {
        Optional<Station> optionalStationEmpty = stationRepository.findById(stationId);
        if(optionalStationEmpty.isEmpty()){
            throw new StationDoesntExist("station is empty");
        }



        Segment entity = getSegment(segmentId);
        Station entityStation = optionalStationEmpty.get();
        entity.setStationTwo(entityStation);
        Segment resEntity = segmentRepository.save(entity);
        return SegmentMapper.toDto(resEntity);
    }

    @Override
    @Transactional
    public StationDTO getFirstStation(Long segmentId) {
        Segment segment = getSegment(segmentId);
        return StationMapper.toDto(segment.getStationOne());

    }

    @Override
    @Transactional
    public StationDTO getSecondStation(Long segmentId) {
        Segment segment = getSegment(segmentId);
        return StationMapper.toDto(segment.getStationTwo());
    }

    @Override
    @Transactional
    public List<LineDTO> getLines(Long segmentId) {
        Segment segment = getSegment(segmentId);
        return segment
                .getEnrollments()
                .stream()
                .map(EnrollmentLineSegment::getLine)
                .map(LineMapper::toDto)
                .toList();
    }


    private Segment getSegment(Long segmentId){
        Optional<Segment> optionalSegmentEmpty = segmentRepository.findById(segmentId);
        if(optionalSegmentEmpty.isEmpty()){
            throw new SegmentDoesntExist("segment is empty");
        }
        return optionalSegmentEmpty.get();
    }

    public Segment checkSegmentAmongTwoStations(Long stationId1, Long stationId2){

        Optional<Station> station = stationRepository.findById(stationId1);
        if(station.isEmpty()){
            throw new StationDoesntExist("station is empty");
        }

        Station station1 = station.get();

        List<Segment> list1 = station1.getSegmentsOne().stream().filter(
                (s) -> {
                    return s.getStationTwo().getId().equals(stationId2);
                }
        ).toList();

        List<Segment> list2 = station1.getSegmentsTwo().stream().filter(
                (s) -> {
                    return s.getStationOne().getId().equals(stationId2);
                }
        ).toList();

        if(!list1.isEmpty()){
            return list1.getFirst();
        }

        if(!list2.isEmpty()){
            return list2.getFirst();
        }

        return null;
    }
}
