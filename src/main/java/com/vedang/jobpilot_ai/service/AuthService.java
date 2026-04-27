package com.vedang.jobpilot_ai.service;

import com.vedang.jobpilot_ai.dto.request.LoginRequest;
import com.vedang.jobpilot_ai.dto.request.SignupRequest;
import com.vedang.jobpilot_ai.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
}
