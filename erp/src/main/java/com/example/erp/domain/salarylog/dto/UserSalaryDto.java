package com.example.erp.domain.salarylog.dto;

import java.time.LocalDate;

public record UserSalaryDto(
        LocalDate receivedDate,
        int salary,
        int bonus,
        int totalSalary
) {
}
