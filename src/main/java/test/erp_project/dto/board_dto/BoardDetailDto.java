package test.erp_project.dto.board_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailDto {
    private Long boardNum;
    private String deptName;
    private String positionName;
    private String name;
    private String title;
    private String content;
    private LocalDate createDate;
    private List<BoardAnswerDetailDto> answerList;
}
