package com.example.railway_manager.controller;


import com.example.railway_manager.service.TestStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @Autowired
    public TestStationService testStationService;

    @GetMapping("/test")
    public void test(){
        testStationService.createTestData();
        System.out.println("заработало");
    }
}
