package com.example.sellcar_spring.services.auth;

import com.example.sellcar_spring.dto.SignUpRequest;
import com.example.sellcar_spring.dto.UserDTO;

public interface AuthService {
    UserDTO signup(SignUpRequest signupRequest);
    Boolean hasUserwithEmail(String email);
}
