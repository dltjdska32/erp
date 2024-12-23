package test.erp_project.dto.work_dto;

import lombok.*;
import test.erp_project.domain.work_log.Status;

import java.time.LocalDate;
import java.time.LocalTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWorkLog {

    private Long workLogNum;
    private LocalDate workDate;
    private Status status;
    private LocalTime startTime;
    private LocalTime endTime;
}
