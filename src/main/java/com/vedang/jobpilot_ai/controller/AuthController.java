package com.vedang.jobpilot_ai.controller;

import com.vedang.jobpilot_ai.dto.request.LoginRequest;
import com.vedang.jobpilot_ai.dto.request.SignupRequest;
import com.vedang.jobpilot_ai.dto.response.AuthResponse;
import com.vedang.jobpilot_ai.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request){
        AuthResponse response = authService.signup(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        AuthResponse response = authService.login(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
