package com.example.railway_manager.service.secure;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    String generateToken(String username);

    String extractUserName(String token);

    boolean validateToken(String token, UserDetails userDetails);

}
