package com.vedang.jobpilot_ai.repository;

import com.vedang.jobpilot_ai.entity.AiHistory;
import com.vedang.jobpilot_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiHistoryRepository extends JpaRepository<AiHistory, Long> {
    List<AiHistory> findByUser(User user);


}
