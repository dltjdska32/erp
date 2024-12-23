package test.erp_project.domain.dept;

import jakarta.persistence.*;
import lombok.*;
import test.erp_project.domain.user.User;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_dept_name", columnList = "dept_name")
})
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_num")
    private Long deptNum;

    @Column(nullable = false, name = "dept_name")
    private String deptName;


    protected Dept() {}

    public Dept(Long deptNum, String deptName) {
        this.deptNum = deptNum;
        this.deptName = deptName;
    }



}
