package test.erp_project.domain.position;


import jakarta.persistence.*;
import lombok.*;
import test.erp_project.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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




    protected Position() {
    }

    public Position(Long positionNum, String positionName, int leaveDay, int basicSalary, int leavePay) {
        this.positionNum = positionNum;
        this.positionName = positionName;
        this.leaveDay = leaveDay;
        this.basicSalary = basicSalary;
        this.leavePay = leavePay;
    }


}
