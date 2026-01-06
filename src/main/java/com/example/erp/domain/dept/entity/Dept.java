package com.example.erp.domain.dept.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 제어자를 protected로 설정
@AllArgsConstructor
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

    public static Dept createDefaultDept(String deptName) {
        return Dept.builder()
                .deptName(deptName)
                .build();
    }

}
