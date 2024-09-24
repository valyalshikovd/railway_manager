package com.example.railway_manager.service.secure;

import com.example.railway_manager.dto.UserDto;
import com.example.railway_manager.model.security.Users;

public interface UserService {

    UserDto registerUser(UserDto user);

    String verify(UserDto user);
    String registerAndVerifyUser(UserDto user);
}
