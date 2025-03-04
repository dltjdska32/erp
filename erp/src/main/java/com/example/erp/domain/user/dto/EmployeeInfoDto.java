package com.example.erp.domain.user.dto;

public record EmployeeInfoDto(
        Long userNum,
        String name,
        String tel,
        String email,
        String deptName,
        String positionName
) {
}
