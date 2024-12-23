package test.erp_project.domain.work_log;

import jakarta.persistence.*;
import lombok.*;
import test.erp_project.domain.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

}
