package com.example.erp.domain.receivedmail.entity;

import com.example.erp.domain.mail.entity.Mail;
import com.example.erp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 제어자를 protected로 설정
@AllArgsConstructor
@Table(name = "received_mail")
public class ReceivedMail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "received_num")
    private Long receivedNum;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mail_num")
    private Mail mail;

    @Builder.Default
    private boolean isDeleted = false;


    public ReceivedMail createDefaultReceivedMail(User user, Mail mail, boolean isDeleted) {
        return ReceivedMail.builder()
                .user(user)
                .mail(mail)
                .isDeleted(isDeleted)
                .build();
    }

    public void deleteMail(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}

