package com.example.erp.domain.worklog.dto;

import com.example.erp.domain.worklog.entity.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public record UserWorkLogDto(
        Long workLogNum,
        LocalDate workDate,
        Status status,
        LocalTime startTime,
        LocalTime endTime
) {
}
