package com.example.erp.domain.receivedmail.exception;

import com.example.erp.global.exception.code.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReceivedMailException implements ExceptionCode {

    RECEIVED_MAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "받은 메일을 찾을수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
