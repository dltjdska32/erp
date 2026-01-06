package com.example.erp.global.responseDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public record ResponseFormat<T> (
        int code,
        String message,
        T data
){

    // 성공시 Data에 값을 추가할때 사용
    public static <T> ResponseFormat<T> of(String message, T data) {
        return new ResponseFormat<>(200, message, data);
    }

    // 성공하고 데이터를 넣어줄 필요 없을때 사용
    public static <T> ResponseFormat<T> of(String message) {
        return new ResponseFormat<>(200, message, null);
    }

    // 실패하고 데이터를 넣어줄 필요 있을때 사용
    public static <T> ResponseFormat<T> fail(Integer code, String message, T data) {
        return new ResponseFormat<>(code, message, data);
    }

    // 실패하고 데이터를 넣어줄 필요 없을때
    public static <T> ResponseFormat<T> fail(Integer code, String message) {
        return new ResponseFormat<>(code, message, null);
    }
}
