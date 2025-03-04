package com.example.erp.domain.sendmail.exception;

import com.example.erp.global.exception.code.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SendMailException implements ExceptionCode {

    Send_MAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "보낸 메일을 찾을수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
