package test.erp_project.dto.work_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.erp_project.domain.work_log.Status;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRespDto {
    private Long workLogNum;
    private LocalTime startTime;
    private Status status;
    private boolean success;
    private String message;
}
