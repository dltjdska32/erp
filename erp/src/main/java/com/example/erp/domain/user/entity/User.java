package com.example.erp.domain.user.entity;

import com.example.erp.domain.dept.entity.Dept;
import com.example.erp.domain.position.entity.Position;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Getter
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

    public static User createDefaultUser(String id,
                                  String password,
                                  String name,
                                  String tel,
                                  String email,
                                  Role role,
                                  int remainedLeave,
                                  Position position,
                                  Dept dept,
                                  String idPhotoUploadName,
                                  String idPhotoStoredName) {
        return User.builder()
                .userId(id)
                .password(password)
                .name(name)
                .tel(tel)
                .email(email)
                .role(role)
                .remainedLeave(remainedLeave)
                .position(position)
                .dept(dept)
                .idPhotoStoredName(idPhotoStoredName)
                .idPhotoUploadName(idPhotoUploadName)
                .build();
    }

    public void updateUserInfo (Dept dept, Position position, int remainedLeave) {
        this.dept = dept;
        this.position = position;
        this.remainedLeave = remainedLeave;
    }

    public void updateRemainedLeave(int remainedLeave) {
        this.remainedLeave = remainedLeave;
    }
}
