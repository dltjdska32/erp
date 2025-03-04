package com.example.erp.global.exception.handler;

import com.example.erp.global.exception.custom.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 뷰 렌더링용 예외 처리기
@ControllerAdvice
@Slf4j
public class ViewExceptionHandler {
    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public String handleException(CustomException e, Model model) {
        log.error("Unexpected error occurred: {}", e.getExceptionCode().getMessage(), e);
        model.addAttribute("errorMessage", e.getExceptionCode().getMessage());
        model.addAttribute("statusCode", e.getExceptionCode().getHttpStatus().value());


        return "error/error-page";
    }

}