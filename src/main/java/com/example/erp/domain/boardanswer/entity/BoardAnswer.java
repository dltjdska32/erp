package com.example.erp.domain.boardanswer.entity;

import com.example.erp.domain.board.entity.Board;
import com.example.erp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 제어자를 protected로 설정
@AllArgsConstructor
@Table(name = "board_answer")
public class BoardAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_num")
    private Long answerNum;

    private String content;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_num")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private User user;

    public static BoardAnswer createDefaultBoardAnswer(String content, LocalDate createdDate, Board board, User user) {
        return BoardAnswer.builder()
                .content(content)
                .createdDate(createdDate)
                .board(board)
                .user(user)
                .build();
    }


}
