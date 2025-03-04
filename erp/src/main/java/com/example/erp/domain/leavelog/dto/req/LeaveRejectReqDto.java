package com.example.erp.domain.leavelog.dto.req;

public record LeaveRejectReqDto(
        Long leaveNum,
        String acceptStatus
) {
}
