package com.example.erp.domain.leavelog.service;

import com.example.erp.domain.leavelog.dto.ForRequestLeaveDto;
import com.example.erp.domain.leavelog.dto.LeaveLogOfAdminDto;
import com.example.erp.domain.leavelog.dto.LeaveLogOfUserDto;
import com.example.erp.domain.leavelog.dto.req.LeaveAcceptReqDto;
import com.example.erp.domain.leavelog.dto.req.LeaveApplyReqDto;
import com.example.erp.domain.leavelog.dto.resp.LeaveAcceptRespDto;
import com.example.erp.domain.leavelog.dto.resp.LeaveApplyRespDto;
import com.example.erp.domain.leavelog.dto.resp.LeaveRejectRespDto;
import com.example.erp.domain.leavelog.entity.LeaveLog;
import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaveLogFacadeService {
    private final LeaveLogService leaveLogService;
    private final UserService userService;
    public Page<LeaveLogOfAdminDto> findLeaveLogOfAdmminDto(Pageable pageable) {
        return leaveLogService.findLeaveLogOfAdminDto(pageable);
    }

    public Page<LeaveLogOfAdminDto> getLeaveLogsOfAdminByName(String name, Pageable pageable) {
        return leaveLogService.getLeaveLogsOfAdminByName(name, pageable);
    }

    public LeaveRejectRespDto rejectLeaveLog(Long leaveNum) {
        leaveLogService.rejectLeaveLog(leaveNum);
        return new LeaveRejectRespDto(true, "휴가 거절");
    }

    public LeaveAcceptRespDto acceptLeave(LeaveAcceptReqDto leaveAcceptReqDto) {
        Long leaveNum = leaveAcceptReqDto.leaveNum();
        int remainedLeave = leaveAcceptReqDto.remainedLeave();
        int requestDays = leaveAcceptReqDto.diffInDays();
        String userId = leaveAcceptReqDto.userId();

        // 남은휴가일수 계산
        remainedLeave -= requestDays;

        if(remainedLeave < 0) {
            return new LeaveAcceptRespDto(false, null,"남은 휴가일수 부족으로 거절" );
        }

        leaveLogService.acceptLeave(leaveNum);
        userService.updateRemainedLeave(userId, remainedLeave);
        return new LeaveAcceptRespDto(true, remainedLeave, "휴가 승인");
    }

    public Page<LeaveLogOfUserDto> findLeaveLogOfUserDtos(Pageable pageable, Long userNum) {
        User user = userService.findUserById(userNum);
        return leaveLogService.findLeaveLogOfUserDtosByUser(pageable, user);
    }

    public LeaveApplyRespDto leaveRequest(UserInfo userInfo, LeaveApplyReqDto leaveApplyReqDto) {
        LocalDate reqDate = LocalDate.now();
        LocalDate startDate = leaveApplyReqDto.getStartDate();
        LocalDate endDate = leaveApplyReqDto.getEndDate();

        User user = userService.findUserById(userInfo.getUserNum());

        long period = 0L;

        // 두 날짜 간 총 일수 계산
        if (startDate != null && endDate != null) {
            // 두 날짜 간 총 일수 계산
            period = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        } else {
            return LeaveApplyRespDto.builder()
                    .success(false)
                    .message("시작 날짜와 끝나는 날짜가 모두 설정되어 있어야 합니다.")
                    .build();
        }

        if(period > user.getRemainedLeave()) {

            return LeaveApplyRespDto.builder()
                    .success(false)
                    .message(user.getName() + "님의 신청일수가 남은 휴가일수를 초과했습니다.")
                    .build();
        }
        LeaveLog leaveLog = LeaveLog.builder()
                .user(user)
                .requestDate(reqDate)
                .endDate(endDate)
                .startDate(startDate)
                .checkStatus(false)
                .acceptanceStatus(false)
                .build();

        leaveLogService.save(leaveLog);
        return LeaveApplyRespDto.builder()
                .success(true)
                .message("휴가신청 완료.")
                .build();
    }
}
