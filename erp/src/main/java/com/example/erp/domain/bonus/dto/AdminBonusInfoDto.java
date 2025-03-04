package com.example.erp.domain.bonus.dto;

import java.time.LocalDate;

public record AdminBonusInfoDto (
        Long bonusNum,
        LocalDate receivedDate,
        int bonus,
        String name,
        String deptName,
        String positionName
) {
}
