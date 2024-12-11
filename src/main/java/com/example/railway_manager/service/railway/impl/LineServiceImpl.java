package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.LineDTO;
import com.example.railway_manager.dto.railways.SegmentDTO;
import com.example.railway_manager.dto.railways.StationDTO;
import com.example.railway_manager.exception.LineDoesntExist;
import com.example.railway_manager.exception.SegmentDoesntExist;
import com.example.railway_manager.exception.StationDoesntExist;
import com.example.railway_manager.exception.WrongLineException;
import com.example.railway_manager.mapper.LineMapper;
import com.example.railway_manager.mapper.SegmentMapper;
import com.example.railway_manager.mapper.StationMapper;
import com.example.railway_manager.model.*;
import com.example.railway_manager.repository.railway.*;
import com.example.railway_manager.service.railway.LineService;
import com.example.railway_manager.service.railway.SegmentService;
import com.example.railway_manager.service.railway.StationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LineServiceImpl implements LineService {

    private final LineRepository lineRepository;
    private final StationService stationService;
    private final SegmentService segmentService;
    private final SegmentRepository segmentRepository;
    private final StationRepository stationRepository;
    private final EnrollmentLineSegmentRepository enrollmentLineSegmentRepository;
    private final EnrollmentLineStationRepository enrollmentLineStationRepository;

    @Override
    @Transactional
    public LineDTO createLine(LineDTO lineDTO) {

        Line entity = new Line();
        entity.setName(lineDTO.getName());
        entity.setSummary_weight(100d);


        List<EnrollmentLineSegment> enrollmentsSegments = lineDTO.getSegments()
                        .stream()
                        .map(segmentDTO -> {
                            EnrollmentLineSegment enrollmentLineSegment = new EnrollmentLineSegment();
                            enrollmentLineSegment.setLine(entity);
                            Optional<Segment> segment = segmentRepository.findById(segmentDTO.getId());
                            if(segment.isEmpty()){
                                throw new SegmentDoesntExist(segmentDTO.getId().toString());
                            }
                            enrollmentLineSegment.setSegment(segment.get());
                            return enrollmentLineSegment;
                        }
                        ).toList();


        List<EnrolmentLineStations> enrolmentsStations = lineDTO.getStations()
                        .stream()
                        .map(
                                stationDTO -> {
                                    EnrolmentLineStations enrolmentLineStations = new EnrolmentLineStations();
                                    enrolmentLineStations.setLine(entity);
                                    Optional<Station> station = stationRepository.findById(stationDTO.getId());
                                    if(station.isEmpty()){
                                        throw new StationDoesntExist(stationDTO.getId().toString());
                                    }
                                    enrolmentLineStations.setStation(station.get());
                                    return enrolmentLineStations;
                                }
                        )
                        .toList();



        int enrollmentsSegmentsSize = enrollmentsSegments.size();
        if(enrollmentsSegmentsSize != enrolmentsStations.size() - 1){
            throw new WrongLineException("Не правильный порядок сегментов");
        }

        for(int i = 0; i < enrollmentsSegmentsSize; i++){
            EnrollmentLineSegment enrollmentLineSegment = enrollmentsSegments.get(i);
            if(
                    !enrollmentLineSegment.getSegment().getStationOne().getId().equals(enrolmentsStations.get(i).getStation().getId())
            && !enrollmentLineSegment.getSegment().getStationTwo().getId().equals(enrolmentsStations.get(i).getStation().getId())){
                throw new WrongLineException("Не правильный порядок сегментов");
            }
        }

        enrollmentLineStationRepository.saveAll(enrolmentsStations);
        enrollmentLineSegmentRepository.saveAll(enrollmentsSegments);

        entity.setEnrollmentLineStations(enrolmentsStations);
        entity.setEnrollmentLineSegments(enrollmentsSegments);

        lineRepository.save(entity);
        return LineMapper.toDto(entity);
    }

    @Override
    public LineDTO deleteLine(Long id) {

        Optional<Line> optionalLine = lineRepository.findById(id);
        if(optionalLine.isEmpty()){
            throw new LineDoesntExist(id.toString());
        }

        lineRepository.delete(optionalLine.get());

        return LineMapper.toDto(optionalLine.get());
    }

    @Override
    public LineDTO getLineById(Long id) {

        Optional<Line> lineOpt = lineRepository.findById(id);
        if(lineOpt.isEmpty()){
            throw new LineDoesntExist("id");
        }
        calculateDates(lineOpt.get(), Instant.now());
        return LineMapper.toDto(lineOpt.get());
    }

    @Override
    public LineDTO getLineByName(String name) {

        Line lineEntity = lineRepository.findByName(name);
        if(lineEntity == null){
            throw new LineDoesntExist(name);
        }

        return LineMapper.toDto(lineEntity);
    }

    @Override
    public List<LineDTO> getAllLines() {

        return lineRepository
                .findAll()
                .stream()
                .map(LineMapper::toDto)
                .toList();

    }

    @Override
    @Transactional
    public List<StationDTO> getStationsByLineId(Long id) {

        Optional<Line> lineOptional = lineRepository.findById(id);
        if(lineOptional.isEmpty()){
            throw new LineDoesntExist("line doesnt exist");
        }
        Line lineEntity = lineOptional.get();
        return lineEntity
                .getEnrollmentLineStations()
                .stream()
                .map(EnrolmentLineStations::getStation)
                .map(StationMapper::toDto)
                .toList();
    }

    @Override
    public List<SegmentDTO> getSegmentsByLineId(Long id) {

        Optional<Line> lineOptional = lineRepository.findById(id);
        if(lineOptional.isEmpty()){
            throw new LineDoesntExist("line doesnt exist");
        }
        Line lineEntity = lineOptional.get();
        return lineEntity
                .getEnrollmentLineSegments()
                .stream()
                .map(EnrollmentLineSegment::getSegment)
                .map(SegmentMapper::toDto)
                .toList();
    }


    public HashMap<Long, Pair<Instant, Instant>> calculateDates(Line line, Instant dateFirst){

       HashMap<Long, Pair<Instant, Instant>> map = new HashMap<>();

       int hours = 0;
       Instant currDare = dateFirst;
       Instant tmpDate = null;

       List<EnrollmentLineSegment> enrollmentLineSegments = line.getEnrollmentLineSegments().stream().toList();
       for (EnrollmentLineSegment enrollmentLineSegment : enrollmentLineSegments){


           Segment segment = enrollmentLineSegment.getSegment();

           Integer currComplexity =  segment.getComplexity();
           Long idSegment = segment.getId();

           tmpDate = currDare;
           currDare = currDare.plusSeconds(currComplexity * 3600);

           Pair<Instant, Instant> pair = Pair.of(tmpDate, currDare);
           map.put(idSegment, pair);
       }


        return map;
    }

}
