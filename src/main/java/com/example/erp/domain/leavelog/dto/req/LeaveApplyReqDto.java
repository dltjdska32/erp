package com.example.erp.domain.leavelog.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplyReqDto {

    private LocalDate startDate;
    private LocalDate endDate;
}
