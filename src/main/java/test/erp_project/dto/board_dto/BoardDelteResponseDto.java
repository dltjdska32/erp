package test.erp_project.dto.board_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardDelteResponseDto {

    private String message;
    private boolean success;
}
