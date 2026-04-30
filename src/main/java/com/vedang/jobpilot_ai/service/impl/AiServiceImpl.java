package com.vedang.jobpilot_ai.service.impl;

import com.vedang.jobpilot_ai.dto.request.AiRequest;
import com.vedang.jobpilot_ai.dto.response.AiResponse;
import com.vedang.jobpilot_ai.entity.AiHistory;
import com.vedang.jobpilot_ai.entity.Application;
import com.vedang.jobpilot_ai.entity.User;
import com.vedang.jobpilot_ai.entity.enums.AiFeatureType;
import com.vedang.jobpilot_ai.exception.ResourceNotFoundException;
import com.vedang.jobpilot_ai.repository.AiHistoryRepository;
import com.vedang.jobpilot_ai.repository.ApplicationRepository;
import com.vedang.jobpilot_ai.service.AiService;
import com.vedang.jobpilot_ai.util.AuthUtil;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiServiceImpl implements AiService {
    private final ChatClient chatClient;
    private final AiHistoryRepository aiHistoryRepository;
    private final ApplicationRepository applicationRepository;
    private final AuthUtil authUtil;

    public AiServiceImpl(ChatClient.Builder chatClientBuilder, AiHistoryRepository aiHistoryRepository,
                         ApplicationRepository applicationRepository, AuthUtil authUtil){
        this.chatClient = chatClientBuilder.build();
        this.aiHistoryRepository = aiHistoryRepository;
        this.applicationRepository = applicationRepository;
        this.authUtil = authUtil;
    }

    @Override
    public AiResponse improveResume(AiRequest request){
        String prompt = """
                You are an expert resume writer.
                Improve the following resume bullet point to be more impactful, 
                quantified, and ATS-friendly. Return only the improved version, 
                nothing else.
                
                Bullet point: %s
                """.formatted(request.getInputText());

        return processAiRequest(request, prompt, AiFeatureType.RESUME_IMPROVER);
    }

    @Override
    public AiResponse analyzeJd(AiRequest request){
        String prompt = """
                You are an expert job coach.
                Analyze the following job description and extract:
                1. Top 5 required technical skills
                2. Top 3 soft skills
                3. Key responsibilities in 3 bullet points
                4. One line summary of the role
                
                Job Description: %s
                """.formatted(request.getInputText());

        return processAiRequest(request, prompt, AiFeatureType.JD_ANALYZER);

    }

    @Override
    public AiResponse generateInterviewQuestions(AiRequest request){
        String prompt = """
                You are an expert technical interviewer.
                Based on the following job description or role, generate 
                10 likely interview questions — mix of technical and behavioral.
                Number each question.
                
                Role/JD: %s
                """.formatted(request.getInputText());

        return processAiRequest(request, prompt, AiFeatureType.INTERVIEW_GENERATOR);

    }


    private AiResponse processAiRequest(AiRequest request, String prompt, AiFeatureType featureType){

        User user = authUtil.getCurrentUser();

        String outputText = chatClient.prompt(prompt).call().content();

        AiHistory aiHistory = new AiHistory();

        aiHistory.setUser(user);
        aiHistory.setFeatureType(featureType);
        aiHistory.setInputText(request.getInputText());
        aiHistory.setOutputText(outputText);

        if(request.getApplicationId() != null){
            Application application = applicationRepository.findById(request.getApplicationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
            aiHistory.setApplication(application);

        }

        AiHistory saved = aiHistoryRepository.save(aiHistory);

        return new AiResponse(
                saved.getInputText(),
                saved.getOutputText(),
                saved.getFeatureType().name(),
                saved.getCreatedAt()
        );



    }

    @Override
    public List<AiResponse> getHistory(){
        User user = authUtil.getCurrentUser();

        List<AiHistory> histories = aiHistoryRepository.findByUser(user);

        List<AiResponse> responses = new ArrayList<>();

        for(AiHistory h : histories){
            responses.add(new AiResponse(
                    h.getInputText(),
                    h.getOutputText(),
                    h.getFeatureType().name(),
                    h.getCreatedAt()
            ));
        }

        return responses;
    }

}
