package test.erp_project.dto.leave_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ForRequestLeaveDto {

    private LocalDate requestDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private String userId;


}
