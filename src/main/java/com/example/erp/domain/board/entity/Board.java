package com.example.erp.domain.board.entity;

import com.example.erp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@AllArgsConstructor //모든컬럼 생성자 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 제어자를 protected로 설정
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_num")
    private Long boardNum;

    @Column(nullable = false) // null값 허용x
    private String title;

    private String contents;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private User user;

    public static Board createDefaultBoard(String title, String contents, LocalDate createdDate, User user) {
        return Board.builder()
                .user(user)
                .createdDate(createdDate)
                .title(title)
                .contents(contents)
                .build();
    }



}
