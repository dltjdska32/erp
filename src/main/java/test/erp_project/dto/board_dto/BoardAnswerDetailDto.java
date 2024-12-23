package test.erp_project.dto.board_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardAnswerDetailDto {

    private String name;
    private String answer;
    private LocalDate createDate;
}
