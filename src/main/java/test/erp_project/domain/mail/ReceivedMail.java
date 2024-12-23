package test.erp_project.domain.mail;

import jakarta.persistence.*;
import lombok.*;
import test.erp_project.domain.user.User;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
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

    private boolean isDeleted = false;

}

