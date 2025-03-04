package com.example.erp.domain.bonus.entity;

import com.example.erp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 제어자를 protected로 설정
@Getter
@Builder
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bonusNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNum")
    private User user;

    private LocalDate receivedDate;

    private int performanceBonus;

    public static Bonus createDefaultBonus(User user, LocalDate receivedDate, int performanceBonus) {
        return Bonus.builder()
                .performanceBonus(performanceBonus)
                .receivedDate(receivedDate)
                .user(user)
                .build();
    }
}
