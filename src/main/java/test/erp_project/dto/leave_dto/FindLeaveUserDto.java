package test.erp_project.dto.leave_dto;

import lombok.*;

import java.time.LocalDate;

@Getter@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindLeaveUserDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean acceptStatus;
    private Long userNum;
}
