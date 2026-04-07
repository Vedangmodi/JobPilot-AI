package com.vedang.jobpilot_ai.util;

import com.vedang.jobpilot_ai.dto.request.ApplicationRequest;
import com.vedang.jobpilot_ai.dto.response.ApplicationResponse;
import com.vedang.jobpilot_ai.entity.Application;

public class MapperUtil {
    public static Application applicatonRequestToApplication(ApplicationRequest applicationRequest) {
        Application application = new Application();

        application.setCompanyName(applicationRequest.getCompanyName());
        application.setRoleTitle(applicationRequest.getRoleTitle());
        application.setJobLink(applicationRequest.getJobLink());
        application.setLocation(applicationRequest.getLocation());
        application.setSalary(applicationRequest.getSalary());
        application.setStatus(applicationRequest.getStatus());
        application.setSource(applicationRequest.getSource());
        application.setNotesSummary(applicationRequest.getNotesSummary());
        application.setAppliedDate(applicationRequest.getAppliedDate());

        return application;
    }

    public static ApplicationResponse applicationToApplicationResponse(Application application) {
        ApplicationResponse applicationResponse = new ApplicationResponse();

        applicationResponse.setId(application.getId());
        applicationResponse.setCompanyName(application.getCompanyName());
        applicationResponse.setRoleTitle(application.getRoleTitle());
        applicationResponse.setJobLink(application.getJobLink());
        applicationResponse.setLocation(application.getLocation());
        applicationResponse.setSalary(application.getSalary());
        applicationResponse.setStatus(application.getStatus());
        applicationResponse.setSource(application.getSource());
        applicationResponse.setNotesSummary(application.getNotesSummary());
        applicationResponse.setAppliedDate(application.getAppliedDate());
        applicationResponse.setJobDescription(application.getJobDescription());
        applicationResponse.setCreatedAt(application.getCreatedAt());
        applicationResponse.setUpdatedAt(application.getUpdatedAt());

        return applicationResponse;
    }
}