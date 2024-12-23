package test.erp_project.dto.salary_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddBonusDto {
    private Long userNum;
    private int bonus;
    private LocalDate receivedDate;
}
