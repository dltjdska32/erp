package test.erp_project.dto.salary_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminBonusInfo {

    private Long bonusNum;
    private LocalDate receivedDate;
    private int bonus;
    private String name;
    private String deptName;
    private String positionName;
}
