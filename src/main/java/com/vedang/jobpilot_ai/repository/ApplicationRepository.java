package com.vedang.jobpilot_ai.repository;

import com.vedang.jobpilot_ai.entity.Application;
import com.vedang.jobpilot_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

   List<Application> findByUser(User user);
}
