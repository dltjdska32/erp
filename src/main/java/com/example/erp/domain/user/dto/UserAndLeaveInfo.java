package com.example.erp.domain.user.dto;

import com.example.erp.domain.user.entity.User;
import lombok.*;

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
