package com.example.erp.domain.leavelog;

import com.example.erp.global.exception.code.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LeaveLogException implements ExceptionCode {

    LEAVE_LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "휴가기록을 찾을수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
