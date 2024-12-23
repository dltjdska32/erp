package test.erp_project.domain.board;

import jakarta.persistence.*;
import lombok.*;
import test.erp_project.domain.user.User;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor //모든컬럼 생성자 생성
@NoArgsConstructor
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


}
