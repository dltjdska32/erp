package com.example.erp.domain.worklog.service;

import com.example.erp.domain.user.dto.UserAndLeaveInfo;
import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.user.service.UserService;
import com.example.erp.domain.worklog.dto.UserWorkLogDto;
import com.example.erp.domain.worklog.dto.resp.AttendanceRespDto;
import com.example.erp.domain.worklog.dto.resp.CheckOutRespDto;
import com.example.erp.domain.worklog.entity.Status;
import com.example.erp.domain.worklog.entity.WorkLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkLogFacadeService {
    private final WorkLogService workLogService;
    private final UserService userService;

    /**
     *  1. 매일 6시에 모든 직원들 결근으로 데이터 생성 (휴가자일경우 휴가로 등록)
     *  2. 9시 이전에 출근등록시 출근으로 상태를 바꾸고 출근시간 설정
     *  3. 2시이전에 퇴근할경우 결근으로 상태변경 퇴근시간 설정
     *  4. 2시 ~ 5시 49분 퇴근시 조퇴상태변경 , 퇴근시간 설정
     *  5. 5시 50~ 6시 10분 사이 퇴근시 출근상태 유지 퇴근시간 설정
     * */


    //매일 6시에 모든 직원들 결근으로 데이터생성.
    //휴가 상태일경우 휴가로 상태변경
    public void saveWorks() {
        List< UserAndLeaveInfo> userAndLeaveInfos = userService.findUserAndLeaveInfos();
        LocalDate today = LocalDate.now();

        for (UserAndLeaveInfo userAndLeaveInfo : userAndLeaveInfos) {
            // 현재 날짜에 휴가중일경우 실행
            if(userAndLeaveInfo.getStartDate() != null && userAndLeaveInfo.getEndDate() != null
                    && (!userAndLeaveInfo.getStartDate().isAfter(today) && !userAndLeaveInfo.getEndDate().isBefore(today))
                    && userAndLeaveInfo.isAcceptStatus() == true) {
                WorkLog worklog = WorkLog.builder()
                        .user(userAndLeaveInfo.getUser())
                        .startTime(null)
                        .endTime(null)
                        .workDate(LocalDate.now())
                        .status(Status.LEAVE)
                        .build();
                workLogService.save(worklog);
                continue;
            }

            // 휴가중이 아닐경우 모두 결근으로 등록
            WorkLog workLog = WorkLog.builder()
                    .user(userAndLeaveInfo.getUser())
                    .startTime(null)
                    .endTime(null)
                    .workDate(LocalDate.now())
                    .status(Status.ABSENCE)
                    .build();

            workLogService.save(workLog);
        }
    }

    public Page<UserWorkLogDto> findUserWorkLogByUserNum(Pageable pageable, Long userNum) {
        return workLogService.findUserWorkLogByUserNum(pageable, userNum);
    }

    public AttendanceRespDto updateWorkLog(Long userNum) {
        User user = userService.findUserById(userNum);
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        Optional<WorkLog> workLog = workLogService.findWorkLogByUserAndDate(user, today);


        if(workLog.isEmpty()) {
            return AttendanceRespDto.builder()
                    .message("근태 기록 찾을수 없음.")
                    .success(false)
                    .build();
        }

        // 9시 이전일 경우 출석처리
        if(currentTime.isBefore(LocalTime.of(9, 1))) {

            workLogService.updateWorkLog(user, today, currentTime, Status.ATTENDANCE);

            AttendanceRespDto attendanceRespDto = AttendanceRespDto.builder()
                    .message("출근 처리")
                    .success(true)
                    .status(Status.ATTENDANCE)
                    .startTime(currentTime)
                    .workLogNum(workLog.get().getLogNum())
                    .build();
            return attendanceRespDto;
        }


        // 9시 이후 14시 이전일 경우 지각처리
        if(currentTime.isAfter(LocalTime.of(9, 0)) && currentTime.isBefore(LocalTime.of(14, 1))) {

            workLogService.updateWorkLog(user, today, currentTime, Status.TARDINESS);

            AttendanceRespDto attendanceRespDto = AttendanceRespDto.builder()
                    .message("지각 처리")
                    .success(true)
                    .status(Status.TARDINESS)
                    .startTime(currentTime)
                    .workLogNum(workLog.get().getLogNum())
                    .build();
            return attendanceRespDto;
        }


        // 나머지는 다 결근 유지 출근시간은 기록함.
        workLogService.updateWorkLog(user, today, currentTime, Status.ABSENCE);
        return AttendanceRespDto.builder()
                .success(true)
                .message("결근 처리")
                .startTime(currentTime)
                .status(Status.ABSENCE)
                .workLogNum(workLog.get().getLogNum())
                .build();
    }

    public CheckOutRespDto setGetOffWork(Long userNum) {
        LocalDate today = LocalDate.now();
        User user = userService.findUserById(userNum);
        Optional<WorkLog> workLog = workLogService.findWorkLogByUserAndDate(user, today);
        LocalTime currentTime = LocalTime.now();
        Duration duration = Duration.between(workLog.get().getStartTime(), currentTime);  //근무 시간

        if(workLog.isEmpty()) {
            return CheckOutRespDto.builder()
                    .message("근태 기록 찾을수 없음.")
                    .success(false)
                    .build();
        }

        // 일한(퇴근- 출근) 시간이 4시간 이상일경우이고  2시부터 5시 49분 사이 퇴근을 하면 조퇴처리
        if(duration.toHours() >= 4 && currentTime.isAfter(LocalTime.of(13, 59))
                && currentTime.isBefore(LocalTime.of(17, 50))) {

           workLogService.setGetOffWork(user, today, currentTime, Status.LEAVEPREV);

            return  CheckOutRespDto.builder()
                    .message("조퇴 처리")
                    .success(true)
                    .status(Status.LEAVEPREV)
                    .endTime(currentTime)
                    .workLogNum(workLog.get().getLogNum())
                    .build();
        }

        //현재 시간이 5시 50분 이후이고 6시 10분 이전이면 출근또는 지각처리 유지후 퇴근시간 설정
        if(currentTime.isAfter(LocalTime.of(17,49)) && currentTime.isBefore(LocalTime.of(18, 11))) {

            workLogService.setGetOffWork(user, today, currentTime, null);

            return  CheckOutRespDto.builder()
                    .message("퇴근 처리")
                    .success(true)
                    .endTime(currentTime)
                    .workLogNum(workLog.get().getLogNum())
                    .build();
        }

        workLogService.setGetOffWork(user, today, currentTime, null);
        return  CheckOutRespDto.builder()
                .message("결근 처리")
                .success(true)
                .endTime(currentTime)
                .workLogNum(workLog.get().getLogNum())
                .build();
    }
}
