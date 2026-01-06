package com.example.erp.domain.leavelog.dto.req;

public record LeaveAcceptReqDto(
        Long leaveNum,
        String acceptStatus,
        int remainedLeave,
        int diffInDays,
        String userId
) {
}
