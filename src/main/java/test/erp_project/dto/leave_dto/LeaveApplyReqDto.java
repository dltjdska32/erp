package test.erp_project.dto.leave_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplyReqDto {

    private LocalDate startDate;
    private LocalDate endDate;
}
