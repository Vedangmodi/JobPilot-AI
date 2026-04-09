package com.vedang.jobpilot_ai.service.impl;

import com.vedang.jobpilot_ai.dto.response.DashboardResponse;
import com.vedang.jobpilot_ai.entity.Application;
import com.vedang.jobpilot_ai.entity.User;
import com.vedang.jobpilot_ai.entity.enums.ApplicationStatus;
import com.vedang.jobpilot_ai.exception.ResourceNotFoundException;
import com.vedang.jobpilot_ai.repository.ApplicationRepository;
import com.vedang.jobpilot_ai.repository.UserRepository;
import com.vedang.jobpilot_ai.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public DashboardServiceImpl(UserRepository userRepository, ApplicationRepository applicationRepository){
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    public DashboardResponse getDashboard(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        DashboardResponse response = new DashboardResponse();

        response.setApplied(applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.APPLIED));
        response.setOa(applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.OA));
        response.setInterview(applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.INTERVIEW));
        response.setRejected(applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.REJECTED));
        response.setOffer(applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.OFFER));
        response.setGhosted(applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.GHOSTED));

        Long total = response.getApplied() + response.getOa() + response.getInterview() + response.getRejected() + response.getOffer() + response.getGhosted();

        response.setTotalApplications(total);

        return response;

    }
}
