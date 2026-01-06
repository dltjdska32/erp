package com.example.erp.domain.worklog.dto.resp;

import com.example.erp.domain.worklog.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRespDto {
    private Long workLogNum;
    private LocalTime startTime;
    private Status status;
    private boolean success;
    private String message;
}
