package com.vedang.jobpilot_ai.repository;

import com.vedang.jobpilot_ai.entity.Application;
import com.vedang.jobpilot_ai.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByApplication(Application application);
}
