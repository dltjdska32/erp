package com.example.erp.domain.board.dto;

import com.example.erp.domain.boardanswer.dto.BoardAnswerDetailDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardDetailsAndAnswersDto {
    private Long boardNum;
    private String deptName;
    private String positionName;
    private String name;
    private String title;
    private String content;
    private LocalDate createDate;
    private List<BoardAnswerDetailDto> answerList;
}
