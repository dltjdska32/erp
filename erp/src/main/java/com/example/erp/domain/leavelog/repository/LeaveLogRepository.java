package com.example.erp.domain.leavelog.repository;

import com.example.erp.domain.leavelog.entity.LeaveLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveLogRepository extends JpaRepository<LeaveLog, Long>, LeaveLogRepositoryCustom {
}
