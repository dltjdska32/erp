package com.example.erp.domain.sendmail.entity;


import com.example.erp.domain.mail.entity.Mail;
import com.example.erp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자의 접근 제어자를 protected로 설정
@AllArgsConstructor
public class SendMail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sendNum")
    private Long sendNum;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mai_num")
    private Mail mail;

    @Builder.Default
    private boolean isDeleted = false;

    public SendMail createDefaultSendMail(User user, Mail mail, boolean isDeleted) {
        return SendMail.builder()
                .mail(mail)
                .user(user)
                .isDeleted(isDeleted)
                .build();
    }


    public void deleteMail(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
