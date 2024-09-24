package com.example.railway_manager.service;


import com.example.railway_manager.repository.StationRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TestStationServiceImpl implements TestStationService {

    @Autowired
    private StationRepository stationRepository;

    @Override
    public void createTestData() {

        String fileName = "C:/Users/770vd/Desktop/railway_manager/src/main/resources/static/cities.txt"; // Замените на ваше имя файла
//
//        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                // Обработка каждой строки
//                stationRepository.
//            }
//        } catch (IOException e) {
//            System.out.println("Ошибка при чтении файла: " + e.getMessage());
//        }
    }
}
