package com.vedang.jobpilot_ai.service;

import com.vedang.jobpilot_ai.dto.response.DashboardResponse;
import com.vedang.jobpilot_ai.entity.enums.ApplicationStatus;

public interface DashboardService {
    DashboardResponse getDashboard(Long userId );
}
