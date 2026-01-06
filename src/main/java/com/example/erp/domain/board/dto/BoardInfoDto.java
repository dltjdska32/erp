package com.example.erp.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardInfoDto {

    private Long boardNum;
    private String deptName;
    private String positionName;
    private String name;
    private String title;
    private LocalDate createDate;
}

