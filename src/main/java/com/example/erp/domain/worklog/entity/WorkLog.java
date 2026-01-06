package com.example.erp.domain.worklog.entity;

import com.example.erp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 제어자를 protected로 설정
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_num")
    private Long logNum;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "work_date")
    private LocalDate workDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private User user;

    public WorkLog createDefaultWorkLog(LocalTime startTime,
                                        LocalTime endTime,
                                        Status status,
                                        LocalDate workDate,
                                        User user) {
        return WorkLog.builder()
                .startTime(startTime)
                .endTime(endTime)
                .status(status)
                .workDate(workDate)
                .user(user)
                .build();
    }

    public void updateWorkLog(LocalTime startTime, Status status) {
        this.startTime = startTime;
        this.status = status;
    }

    public void setGetOffWork(LocalTime endTime, Status status) {
        this.endTime = endTime;
        this.status = status;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
