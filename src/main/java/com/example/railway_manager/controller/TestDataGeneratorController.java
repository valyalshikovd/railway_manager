package com.example.railway_manager.controller;


import com.example.railway_manager.service.railway.TestDataGenerator;
import com.example.railway_manager.utils.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestDataGeneratorController {

    private final TestDataGenerator testDataGenerator;

    @GetMapping(ApiPaths.BASE_API + "/generateTestData")
    public void generateTestData() {
        testDataGenerator.generateTestData();
    }

}
