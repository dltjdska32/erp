    package test.erp_project.domain.bonus;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import org.hibernate.annotations.Fetch;
    import test.erp_project.domain.user.User;

    import java.time.LocalDate;

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public class Bonus {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long bonusNum;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="userNum")
        private User user;

        private LocalDate receivedDate;

        private int performanceBonus;
    }
