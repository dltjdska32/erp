package test.erp_project.domain.user;

import jakarta.persistence.*;
import lombok.*;

import test.erp_project.domain.board.Board;
import test.erp_project.domain.dept.Dept;
import test.erp_project.domain.leave_log.LeaveLog;

import test.erp_project.domain.mail.ReceivedMail;
import test.erp_project.domain.position.Position;
import test.erp_project.domain.salary_log.SalaryLog;
import test.erp_project.domain.work_log.WorkLog;
import test.erp_project.dto.user_dto.UserJoinDto;
import test.erp_project.dto.user_dto.UserLoginDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNum;

    @Column(unique = true, nullable = false, name="user_id")
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "remained_leave")
    private int remainedLeave; // 기본 연차 default값 15

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_num")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_num")
    private Dept dept;

    private String idPhotoUploadName;

    private String idPhotoStoredName;
}
