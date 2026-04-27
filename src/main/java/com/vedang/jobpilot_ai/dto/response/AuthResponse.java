package com.vedang.jobpilot_ai.dto.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String name;
    private String email;
    private String message;

    public AuthResponse(String token, String name, String email, String message) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.message = message;
    }
}
