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
public class UserSalaryDto {

    private LocalDate receivedDate;
    private int salary;
    private int bonus;
    private int totalSalary;
}
