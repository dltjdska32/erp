package test.erp_project.domain.leave_log;


import jakarta.persistence.*;
import lombok.*;
import test.erp_project.domain.user.User;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
}
