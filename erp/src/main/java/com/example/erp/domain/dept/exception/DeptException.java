package com.example.erp.domain.dept.exception;

import com.example.erp.global.exception.code.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum DeptException implements ExceptionCode {

    DEPT_NOT_FOUND(HttpStatus.NOT_FOUND, "부서를 찾을수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
