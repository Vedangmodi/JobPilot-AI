package com.vedang.jobpilot_ai.repository;

import com.vedang.jobpilot_ai.entity.Application;
import com.vedang.jobpilot_ai.entity.User;
import com.vedang.jobpilot_ai.entity.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

   List<Application> findByUser(User user);

   Page<Application> findByUser(User user, Pageable pageable);

   @Query("Select a from Application a where a.user = :user " +
           "and (:status is null or a.status = :status) " +
           "and (:search is null or lower(a.companyName) like lower(concat('%' , :search, '%') ) )")
   List<Application> findByUserWithFilter(
           @Param("user") User user,
           @Param("status") ApplicationStatus status,
           @Param("search") String search

   );

   @Query("Select count(a) from Application a where a.userId = :userId and a.status = :status")
   Long countByUserIdAndStatus(@Param("userId") Long userId,
                               @Param("status") ApplicationStatus status);
}
