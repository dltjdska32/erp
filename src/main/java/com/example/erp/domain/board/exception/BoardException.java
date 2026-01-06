package com.example.erp.domain.board.exception;

import com.example.erp.global.exception.code.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardException implements ExceptionCode {

    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
