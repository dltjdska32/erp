package test.erp_project.domain.salary_log;

import jakarta.persistence.*;
import lombok.*;
import test.erp_project.domain.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
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



}
