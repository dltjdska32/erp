package com.example.erp.domain.user.dto.resp;

public record UpdatedUserInfoRespDto(
        Long userNum,
        String deptName,
        String positionName
) {
}
