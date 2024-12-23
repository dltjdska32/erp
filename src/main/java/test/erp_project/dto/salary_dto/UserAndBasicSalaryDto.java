package test.erp_project.dto.salary_dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserAndBasicSalaryDto {
    private Long userNum;
    private String userName;
    private String deptName;
    private String positionName;
    private int basicSalary;
}
