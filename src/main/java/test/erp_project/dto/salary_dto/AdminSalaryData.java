package test.erp_project.dto.salary_dto;

import lombok.*;

import java.time.LocalDate;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminSalaryData {
    private LocalDate receivedDate;
    private Long salaryNum; //월급번호 PK
    private String name; //월급을 지급받은 직원의이름
    private String deptName;
    private String positionName;
    private int basicSalary; // 기본급
    private int bonus;
    private int totalSalary;









}