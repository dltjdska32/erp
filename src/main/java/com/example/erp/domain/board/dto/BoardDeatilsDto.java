package com.example.erp.domain.board.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BoardDeatilsDto {
    private Long boardNum;
    private String deptName;
    private String positionName;
    private String name;
    private String title;
    private String content;
    private LocalDate createDate;
}
