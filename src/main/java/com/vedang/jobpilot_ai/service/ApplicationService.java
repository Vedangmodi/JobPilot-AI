package com.vedang.jobpilot_ai.service;

import com.vedang.jobpilot_ai.dto.request.ApplicationRequest;
import com.vedang.jobpilot_ai.dto.response.ApplicationResponse;
import com.vedang.jobpilot_ai.entity.enums.ApplicationStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApplicationService {
    ApplicationResponse create(ApplicationRequest req);

    List<ApplicationResponse> getAll();

    ApplicationResponse getById(Long id);

    ApplicationResponse update(ApplicationRequest applicationRequest, Long id);

    void delete(Long id);

    List<ApplicationResponse> getAllWithFilter(ApplicationStatus applicationStatus, String search);

    Page<ApplicationResponse> getAllPaginated(int page, int size);
}
