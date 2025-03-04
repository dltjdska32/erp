package com.example.erp.domain.salarylog.repository;

import com.example.erp.domain.salarylog.entity.SalaryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryLogRepository extends JpaRepository<SalaryLog, Long>, SalaryLogRepositoryCustom {
}
