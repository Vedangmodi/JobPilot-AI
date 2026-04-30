package com.vedang.jobpilot_ai.controller;

import com.vedang.jobpilot_ai.dto.request.AiRequest;
import com.vedang.jobpilot_ai.dto.response.AiResponse;
import com.vedang.jobpilot_ai.service.AiService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AiController {
    private final AiService aiService;

    AiController(AiService aiService){
        this.aiService = aiService;
    }

    @PostMapping("/resume-improver")
    public ResponseEntity<AiResponse> improveResume(@Valid @RequestBody AiRequest request){
        AiResponse response = aiService.improveResume(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/jd-analyzer")
    public ResponseEntity<AiResponse> analyzeJd(@Valid @RequestBody AiRequest request){
        AiResponse response = aiService.analyzeJd(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/interview-questions")
    public ResponseEntity<AiResponse> generateInterviewQuestions(@Valid @RequestBody AiRequest request) {
        AiResponse response = aiService.generateInterviewQuestions(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<AiResponse>> getHistory(){
        List<AiResponse> response = aiService.getHistory();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
