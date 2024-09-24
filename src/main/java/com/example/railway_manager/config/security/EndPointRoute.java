package com.example.railway_manager.config.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.List;

@Data
@AllArgsConstructor
public class EndPointRoute {
    private String route;
    private HttpMethod method;
    private List<String> roles;
}
