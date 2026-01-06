package com.example.erp.domain.salarylog.dto;

import java.time.LocalDate;

public record AdminSalaryDto(
        LocalDate receivedDate,
         Long salaryNum, //월급번호 PK
         String name, //월급을 지급받은 직원의이름
         String deptName,
         String positionName,
         int basicSalary, // 기본급
         int bonus,
         int totalSalary
) {
}
