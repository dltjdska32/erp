package com.example.erp.domain.leavelog.entity;


import com.example.erp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 제어자를 protected로 설정
@AllArgsConstructor
@Builder
@Table(name = "leave_log")
public class LeaveLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_num")
    private Long leaveNum;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "acceptance_status")
    private boolean acceptanceStatus;

    private boolean checkStatus;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private User user;

    public LeaveLog createDefaultLeaveLog(LocalDate requestDate,
                                          LocalDate startDate,
                                          LocalDate endDate,
                                          boolean acceptanceStatus,
                                          boolean checkStatus,
                                          User user) {
        return LeaveLog.builder()
                .requestDate(requestDate)
                .startDate(startDate)
                .endDate(endDate)
                .acceptanceStatus(acceptanceStatus)
                .checkStatus(checkStatus)
                .user(user)
                .build();
    }


    public void updateLeave(boolean checkStatus, boolean acceptanceStatus) {
        this.checkStatus = checkStatus;
        this.acceptanceStatus = acceptanceStatus;
    }
}
