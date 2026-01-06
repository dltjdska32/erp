package com.example.erp.domain.user.dto.req;

public record UpdateUserInfoReqDto(
        String email,
        String deptName,
        String positionName,
        String originPosition
) { }
