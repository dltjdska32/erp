package com.example.erp.domain.salarylog.dto;

public record UserAndBasicSalaryDto (
        Long userNum,
        String userName,
        String deptName,
        String positionName,
        int basicSalary
) {
}
