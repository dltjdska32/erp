package com.example.erp.domain.salarylog.entity;

import com.example.erp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.function.LongConsumer;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 제어자를 protected로 설정
@AllArgsConstructor
@Table(name= "salary_log")
public class SalaryLog {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "salary_num")
    private Long salaryNum;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Column(name = "total_salary")
    private int totalSalary;

    private int totalBonus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private User user;

    public static SalaryLog createDefaultSalaryLog(LocalDate receivedDate,
                                            int totalSalary,
                                            int totalBonus,
                                            User user) {
        return SalaryLog.builder()
                .receivedDate(receivedDate)
                .totalSalary(totalSalary)
                .totalBonus(totalBonus)
                .user(user)
                .build();
    }

}
