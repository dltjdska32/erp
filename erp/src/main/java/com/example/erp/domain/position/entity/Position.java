package com.example.erp.domain.position.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 제어자를 protected로 설정
@Table(indexes = {
        @Index(name = "idx_position_name", columnList = "position_name")
})
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_num")
    private Long positionNum;

    @Column(name = "position_name")
    private String positionName;

    @Column(name = "leave_day")
    private int leaveDay;

    @Column(name = "basic_salary")
    private int basicSalary;

    @Column(name = "leave_pay")
    private int leavePay;

    public static Position createDefaultPosition (String positionName, int leaveDay, int basicSalary, int leavePay) {
        return Position.builder()
                .positionName(positionName)
                .basicSalary(basicSalary)
                .leaveDay(leaveDay)
                .leavePay(leavePay)
                .build();
    }

}
