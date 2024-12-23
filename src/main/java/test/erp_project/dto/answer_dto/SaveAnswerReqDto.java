package test.erp_project.dto.answer_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaveAnswerReqDto {
    private Long boardNum;
    private String content;
}
