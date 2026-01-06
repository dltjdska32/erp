package com.example.erp.domain.worklog.exception;

import com.example.erp.global.exception.code.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum WorkLogException implements ExceptionCode {

    WORK_LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "근태기록을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
