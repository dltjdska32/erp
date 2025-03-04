package com.example.erp.domain.boardanswer.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BoardAnswerDetailDto {

    private String name;
    private String answer;
    private LocalDate createDate;
}
