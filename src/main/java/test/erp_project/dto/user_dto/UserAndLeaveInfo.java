package test.erp_project.dto.user_dto;

import lombok.*;
import test.erp_project.domain.user.User;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAndLeaveInfo {
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean acceptStatus;
}
