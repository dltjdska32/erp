package com.example.erp.domain.leavelog.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LeaveApplyRespDto {

    private boolean success;
    private String message;
}
