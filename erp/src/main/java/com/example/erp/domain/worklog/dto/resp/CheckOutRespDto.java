package com.example.erp.domain.worklog.dto.resp;

import com.example.erp.domain.worklog.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckOutRespDto {
    private Long workLogNum;
    private LocalTime endTime;
    private Status status;
    private boolean success;
    private String message;
}
