package com.example.erp.domain.boardanswer.exception;

import com.example.erp.global.exception.code.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardAnswerException implements ExceptionCode {
    BOARD_ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
