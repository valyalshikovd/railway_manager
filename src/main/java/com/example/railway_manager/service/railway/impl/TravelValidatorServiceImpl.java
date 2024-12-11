package com.example.railway_manager.service.railway.impl;

import com.example.railway_manager.dto.railways.ValidateReportDto;
import com.example.railway_manager.model.EnrollmentLineSegment;
import com.example.railway_manager.model.Line;
import com.example.railway_manager.model.Segment;
import com.example.railway_manager.model.Travel;
import com.example.railway_manager.repository.railway.TravelRepository;
import com.example.railway_manager.service.railway.LineService;
import com.example.railway_manager.service.railway.TravelService;
import com.example.railway_manager.service.railway.TravelValidatorService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelValidatorServiceImpl implements TravelValidatorService {

    private final TravelRepository travelRepository;
    private final LineService lineService;

    @Override
    @Transactional
    public ValidateReportDto validateTravel(Travel travel) {
        Line line = travel.getLine();
        HashMap<Long, Pair<Instant, Instant>> sourceDate = lineService.calculateDates(line, travel.getDateDeparture());
        List<EnrollmentLineSegment> enrollmentLineSegments = line.getEnrollmentLineSegments();
        List<Segment> segments = enrollmentLineSegments
                .stream()
                .map(EnrollmentLineSegment::getSegment)
                .toList();
        List<Travel> travels = travelRepository.findAll();
        for (Travel travel1 : travels) {
            HashMap<Long, Pair<Instant, Instant>> currentDates = lineService.calculateDates(line, travel1.getDateDeparture());
            for (EnrollmentLineSegment enrollmentLineSegment : enrollmentLineSegments) {
                for (Segment segment : segments) {
                    if (segment.getId().equals(enrollmentLineSegment.getSegment().getId())) {
                        Instant start1 = sourceDate.get(segment.getId()).getFirst();
                        Instant end1 = sourceDate.get(segment.getId()).getSecond();

                        Instant start2 = currentDates.get(segment.getId()).getFirst();
                        Instant end2 = currentDates.get(segment.getId()).getSecond();

                        if(end1.isAfter(start2) && start2.isBefore(end1)) {
                            return new ValidateReportDto(false, "Пересечение с : " + segment.getId() + "");
                        }
                    }
                }
            }
        }
        return new ValidateReportDto(true, "Успешно");
    }


//    private boolean checkCoincidence(
//            Travel travel1,
//            Travel travel2,
//            Segment segment1,
//            Segment segment2) {
//
//        travel1.
//
//
//    }
}
