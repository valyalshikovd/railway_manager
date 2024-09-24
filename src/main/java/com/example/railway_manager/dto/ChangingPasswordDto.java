package com.example.railway_manager.dto;


import lombok.Data;

@Data
public class ChangingPasswordDto {

    private String oldPassword;
    private String newPassword;
}
