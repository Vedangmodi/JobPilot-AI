package com.vedang.jobpilot_ai.service;

import com.vedang.jobpilot_ai.dto.request.ApplicationRequest;
import com.vedang.jobpilot_ai.dto.response.ApplicationResponse;
import com.vedang.jobpilot_ai.entity.enums.ApplicationStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ApplicationService {
    ApplicationResponse create(ApplicationRequest req, Long userId);

    List<ApplicationResponse> getAll(Long userId);

    ApplicationResponse getById(Long id, Long userId);

    ApplicationResponse update(ApplicationRequest applicationRequest, Long id, Long userId);

    void delete(Long id, Long userId);

    List<ApplicationResponse> getAllWithFilter(Long usedId, ApplicationStatus applicationStatus, String search);

    Page<ApplicationResponse> getAllPaginated(Long userId, int page, int size);
}
