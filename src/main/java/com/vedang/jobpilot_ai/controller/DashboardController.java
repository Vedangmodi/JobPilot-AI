package com.vedang.jobpilot_ai.controller;

import com.vedang.jobpilot_ai.dto.response.DashboardResponse;
import com.vedang.jobpilot_ai.entity.enums.ApplicationStatus;
import com.vedang.jobpilot_ai.service.ApplicationService;
import com.vedang.jobpilot_ai.service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService){
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardResponse> getDashboard(){
        DashboardResponse response =  dashboardService.getDashboard();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
