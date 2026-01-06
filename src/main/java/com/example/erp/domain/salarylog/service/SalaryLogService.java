package com.example.erp.domain.salarylog.service;

import com.example.erp.domain.salarylog.dto.AdminSalaryDto;
import com.example.erp.domain.salarylog.dto.UserAndBasicSalaryDto;
import com.example.erp.domain.salarylog.dto.UserSalaryDto;
import com.example.erp.domain.salarylog.entity.SalaryLog;
import com.example.erp.domain.salarylog.repository.SalaryLogRepository;
import com.example.erp.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SalaryLogService {
    private final SalaryLogRepository salaryLogRepository;

    //어드민 샐러리로그조회
    public Page<AdminSalaryDto> findAllSalaryLogs(Pageable pageable) {
        return salaryLogRepository.findAllSalaryLogs(pageable);
    }

    public Page<AdminSalaryDto> findAllSalaryLogByKeyword(Pageable pageable, String keyword) {
        return salaryLogRepository.findAllSalaryLogByKeyword(pageable, keyword);

    }

    public Page<UserAndBasicSalaryDto> findAllUserAndBasicSalaryDto(Pageable pageable) {
        return salaryLogRepository.findAllUserAndBasicSalaryDto(pageable);
    }

    public Page<UserAndBasicSalaryDto> findAllUserAndBasicsalaryDtoByUserName(Pageable pageable, String name) {
        return salaryLogRepository.findAllUserAndBasicSalaryDtoByUserName(pageable, name);
    }

    public Page<UserSalaryDto> findUserSalaryLogsByUserNum(Pageable pageable, Long userNum) {
        return salaryLogRepository.findUserSalaryLogsByUserNum(pageable, userNum);
    }

    @Transactional
    public void save(User user, int pfBonus) {
        LocalDate today = LocalDate.now();
        int totalSalary = user.getPosition().getBasicSalary() + pfBonus;

        SalaryLog salaryLog = SalaryLog.createDefaultSalaryLog(today, totalSalary, pfBonus, user);

        salaryLogRepository.save(salaryLog);
    }
}
