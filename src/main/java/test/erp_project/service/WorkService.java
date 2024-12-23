package test.erp_project.service;


import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.user.User;
import test.erp_project.domain.work_log.Status;
import test.erp_project.domain.work_log.WorkLog;
import test.erp_project.dto.user_dto.UserAndLeaveInfo;
import test.erp_project.dto.work_dto.AttendanceRespDto;
import test.erp_project.dto.work_dto.CheckOutRespDto;
import test.erp_project.dto.work_dto.UserWorkLog;
import test.erp_project.repository.WorkLogRepository;
import test.erp_project.repository.WorkLogRepositoryImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class WorkService {

    private final WorkLogRepositoryImpl workLogRepositoryImpl;
    private final WorkLogRepository workLogRepository;
    private final UserService userService;
    private final LeaveService leaveService;

    /**
    *  1. 매일 6시에 모든 직원들 결근으로 데이터 생성 (휴가자일경우 휴가로 등록)
     *  2. 9시 이전에 출근등록시 출근으로 상태를 바꾸고 출근시간 설정
     *  3. 2시이전에 퇴근할경우 결근으로 상태변경 퇴근시간 설정
     *  4. 2시 ~ 5시 49분 퇴근시 조퇴상태변경 , 퇴근시간 설정
     *  5. 5시 50~ 6시 10분 사이 퇴근시 출근상태 유지 퇴근시간 설정
    * */



    @Transactional
    //매일 6시에 모든 직원들 결근으로 데이터생성.
    //휴가 상태일경우 휴가로 상태변경
    public void saveWork() {

        List<UserAndLeaveInfo> users = userService.getAllUserAndLeaveInfo();
        LocalDate date = LocalDate.now();

        for(UserAndLeaveInfo userAndLeaveInfo : users) {

            // 현재 날짜에 휴가중일경우 실행
            if(userAndLeaveInfo.getStartDate() != null && userAndLeaveInfo.getEndDate() != null
                    && (!userAndLeaveInfo.getStartDate().isAfter(date) && !userAndLeaveInfo.getEndDate().isBefore(date))
                    && userAndLeaveInfo.isAcceptStatus() == true) {
                WorkLog worklog = WorkLog.builder()
                        .user(userAndLeaveInfo.getUser())
                        .startTime(null)
                        .endTime(null)
                        .workDate(LocalDate.now())
                        .status(Status.LEAVE)
                        .build();
                workLogRepositoryImpl.save(worklog);
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
            workLogRepositoryImpl.save(workLog);
        }
    }

    @Transactional
    // 출근 기록 (사용자 출근 클릭)
    public AttendanceRespDto updateAttendance(Long userNum) {
        LocalDate date = LocalDate.now();
        LocalTime now = LocalTime.now();
        // 사용자를 찾는다.
        User user= userService.findUserByUserNum(userNum);
        WorkLog workLog = workLogRepositoryImpl.findWorkLogByUserAndDate(user, date);


        if(workLog == null) {
            return AttendanceRespDto.builder()
                    .message("근태 기록 찾을수 없음.")
                    .success(false)
                    .build();
        }

        // 9시 이전일 경우 출석처리
        if(now.isBefore(LocalTime.of(9, 1))) {

            workLog.setStartTime(LocalTime.now());
            workLog.setStatus(Status.ATTENDANCE);

            AttendanceRespDto attendanceRespDto = AttendanceRespDto.builder()
                    .message("출근 처리")
                    .success(true)
                    .status(Status.ATTENDANCE)
                    .startTime(now)
                    .workLogNum(workLog.getLogNum())
                    .build();
            return attendanceRespDto;
        }

        // 9시 이후 14시 이전일 경우 지각처리
        if(now.isAfter(LocalTime.of(9, 0)) && now.isBefore(LocalTime.of(14, 1))) {

            workLog.setStartTime(LocalTime.now());
            workLog.setStatus(Status.TARDINESS);

            AttendanceRespDto attendanceRespDto = AttendanceRespDto.builder()
                    .message("지각 처리")
                    .success(true)
                    .status(Status.TARDINESS)
                    .startTime(now)
                    .workLogNum(workLog.getLogNum())
                    .build();
            return attendanceRespDto;
        }

        // 나머지는 다 결근 유지 출근시간은 기록함.
        workLog.setStartTime(LocalTime.now());

        return AttendanceRespDto.builder()
                .success(true)
                .message("결근 처리")
                .startTime(now)
                .status(Status.ABSENCE)
                .workLogNum(workLog.getLogNum())
                .build();
    }


    @Transactional
    // 퇴근 기록 (사용자 퇴근 클릭)
    // 6시 10분 이후에도 퇴근을 하지않는다면, 결근처리
    // 5시 50분부터 6시 10분 사이에 퇴근을 클릭한다면 지각,출석유지 후, 퇴근시간 설정.
    public CheckOutRespDto updateGetOffWork(Long userNum) {
        LocalDate today = LocalDate.now();
        User user = userService.findUserByUserNum(userNum);
        WorkLog workLog = workLogRepositoryImpl.findWorkLogByUserAndDate(user, today);
        LocalTime now = LocalTime.now();
        Duration duration = Duration.between(workLog.getStartTime(), now);  //근무 시간

        if(workLog == null) {
            return CheckOutRespDto.builder()
                    .message("근태 기록 찾을수 없음.")
                    .success(false)
                    .build();
        }

        // 일한(퇴근- 출근) 시간이 4시간 이상일경우이고  2시부터 5시 49분 사이 퇴근을 하면 조퇴처리
        if(duration.toHours() >= 4 && now.isAfter(LocalTime.of(13, 59))
                && now.isBefore(LocalTime.of(17, 50))) {

            workLog.setEndTime(LocalTime.now());
            workLog.setStatus(Status.LEAVEPREV);

            return  CheckOutRespDto.builder()
                    .message("조퇴 처리")
                    .success(true)
                    .status(Status.LEAVEPREV)
                    .endTime(now)
                    .workLogNum(workLog.getLogNum())
                    .build();
        }

        //현재 시간이 5시 50분 이후이고 6시 10분 이전이면 출근또는 지각처리 유지후 퇴근시간 설정
        if(now.isAfter(LocalTime.of(17,49)) && now.isBefore(LocalTime.of(18, 11))) {

            workLog.setEndTime(LocalTime.now());

            return  CheckOutRespDto.builder()
                    .message("퇴근 처리")
                    .success(true)
                    .endTime(now)
                    .workLogNum(workLog.getLogNum())
                    .build();
        }

        workLog.setEndTime(LocalTime.now());
        return  CheckOutRespDto.builder()
                .message("결근 처리")
                .success(true)
                .endTime(now)
                .workLogNum(workLog.getLogNum())
                .build();

    }


    public Page<UserWorkLog> findUserWorkLogByUser(Pageable pageable, User user) {
        return workLogRepository.findUserWorkLogByUser(pageable, user);
    }
}
