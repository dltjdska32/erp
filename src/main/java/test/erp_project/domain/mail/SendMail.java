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

    private boolean isDeleted = false;
}
