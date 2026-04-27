package com.vedang.jobpilot_ai.service.impl;

import com.vedang.jobpilot_ai.dto.response.DashboardResponse;
import com.vedang.jobpilot_ai.entity.Application;
import com.vedang.jobpilot_ai.entity.User;
import com.vedang.jobpilot_ai.entity.enums.ApplicationStatus;
import com.vedang.jobpilot_ai.exception.ResourceNotFoundException;
import com.vedang.jobpilot_ai.repository.ApplicationRepository;
import com.vedang.jobpilot_ai.repository.UserRepository;
import com.vedang.jobpilot_ai.service.DashboardService;
import com.vedang.jobpilot_ai.util.AuthUtil;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final AuthUtil authUtil;

    public DashboardServiceImpl(UserRepository userRepository, ApplicationRepository applicationRepository, AuthUtil authUtil){
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.authUtil = authUtil;
    }

    public DashboardResponse getDashboard(){
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));
        User user = authUtil.getCurrentUser();
        DashboardResponse response = new DashboardResponse();

        response.setApplied(applicationRepository.countByUserIdAndStatus(user.getId(), ApplicationStatus.APPLIED));
        response.setOa(applicationRepository.countByUserIdAndStatus(user.getId(), ApplicationStatus.OA));
        response.setInterview(applicationRepository.countByUserIdAndStatus(user.getId(), ApplicationStatus.INTERVIEW));
        response.setRejected(applicationRepository.countByUserIdAndStatus(user.getId(), ApplicationStatus.REJECTED));
        response.setOffer(applicationRepository.countByUserIdAndStatus(user.getId(), ApplicationStatus.OFFER));
        response.setGhosted(applicationRepository.countByUserIdAndStatus(user.getId(), ApplicationStatus.GHOSTED));

        Long total = response.getApplied() + response.getOa() + response.getInterview() + response.getRejected() + response.getOffer() + response.getGhosted();

        response.setTotalApplications(total);

        return response;

    }
}
