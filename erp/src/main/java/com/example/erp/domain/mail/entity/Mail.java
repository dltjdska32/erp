package com.example.erp.domain.mail.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 제어자를 protected로 설정
@Builder
@AllArgsConstructor
public class Mail {

    @Column(name = "mai_num")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mailNum;

    @Column(nullable = false)
    private String title;

    private String contents;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "is_deleted")
    @Builder.Default
    private boolean isDeleted = false;


    public static Mail createDefaultMail(String title, String contents, LocalDate createdDate, boolean isDeleted) {
        return Mail.builder()
                .contents(contents)
                .title(title)
                .createdDate(createdDate)
                .isDeleted(isDeleted)
                .build();
    }

}
