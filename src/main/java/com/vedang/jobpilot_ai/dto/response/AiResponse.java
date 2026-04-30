package com.vedang.jobpilot_ai.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AiResponse {

    private String inputText;
    private String outputText;
    private String featureType;
    private LocalDateTime createdAt;

    public AiResponse(String inputText, String outputText, String featureType, LocalDateTime createdAt) {
        this.inputText = inputText;
        this.outputText = outputText;
        this.featureType = featureType;
        this.createdAt = createdAt;
    }
}
