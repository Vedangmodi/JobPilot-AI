package com.vedang.jobpilot_ai.service;

import com.vedang.jobpilot_ai.dto.request.AiRequest;
import com.vedang.jobpilot_ai.dto.response.AiResponse;

import java.util.List;

public interface AiService {
    AiResponse improveResume(AiRequest request);
    AiResponse analyzeJd(AiRequest request);
    AiResponse generateInterviewQuestions(AiRequest request);
    List<AiResponse> getHistory();
}
