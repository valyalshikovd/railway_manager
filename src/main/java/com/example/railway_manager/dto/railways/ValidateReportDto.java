package com.example.railway_manager.dto.railways;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidateReportDto {

    private boolean valid;
    private String message;
}
