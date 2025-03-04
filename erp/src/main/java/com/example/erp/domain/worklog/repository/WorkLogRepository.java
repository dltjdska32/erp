package com.example.erp.domain.worklog.repository;

import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.worklog.entity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long>, WorkLogRepositoryCustom {
    Optional<WorkLog> findByUserAndWorkDate(User user, LocalDate today);
}
