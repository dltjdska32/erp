package com.example.erp.domain.leavelog.dto.resp;

public record LeaveAcceptRespDto(
        boolean success,
        Integer remainedLeave,
        String message
) {
}
