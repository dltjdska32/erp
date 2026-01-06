package com.example.erp.domain.position.exception;

import com.example.erp.global.exception.code.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum PositionException implements ExceptionCode {

    POSITION_NOT_FOUND(HttpStatus.NOT_FOUND, "직위를 찾을수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

