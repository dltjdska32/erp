package com.example.erp.domain.salarylog.repository;

import com.example.erp.domain.salarylog.dto.AdminSalaryDto;
import com.example.erp.domain.salarylog.dto.UserAndBasicSalaryDto;
import com.example.erp.domain.salarylog.dto.UserSalaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SalaryLogRepositoryCustom {
    Page<AdminSalaryDto> findAllSalaryLogs(Pageable pageable);

    Page<AdminSalaryDto> findAllSalaryLogByKeyword(Pageable pageable, String keyword);

    Page<UserAndBasicSalaryDto> findAllUserAndBasicSalaryDto(Pageable pageable);

    Page<UserAndBasicSalaryDto> findAllUserAndBasicSalaryDtoByUserName(Pageable pageable, String name);

    Page<UserSalaryDto> findUserSalaryLogsByUserNum(Pageable pageable, Long userNum);
}
