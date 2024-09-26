package com.example.railway_manager.dto.security;


import lombok.Data;

@Data
public class ChangingPasswordDto {

    private String oldPassword;
    private String newPassword;
}
