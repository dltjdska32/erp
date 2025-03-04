/*
package com.example.erp.global.exception.handler;

import com.example.erp.global.exception.code.ExceptionCode;
import com.example.erp.global.exception.custom.CustomException;
import com.example.erp.global.responseDto.ResponseFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestApiGlobalExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseFormat<?>> handleCustomException(CustomException ex) {
        ExceptionCode exceptionCode = ex.getExceptionCode();
        log.info("ErrorMassage={}", exceptionCode.getMessage());
        ResponseFormat<?> response = ResponseFormat.fail(exceptionCode.getHttpStatus().value(), exceptionCode.getMessage());
        return ResponseEntity.status(exceptionCode.getHttpStatus()).body(response);
    }


}
*/
