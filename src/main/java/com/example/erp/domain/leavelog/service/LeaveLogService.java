package com.example.erp.domain.leavelog.service;

import com.example.erp.domain.leavelog.LeaveLogException;
import com.example.erp.domain.leavelog.dto.LeaveLogOfAdminDto;
import com.example.erp.domain.leavelog.dto.LeaveLogOfUserDto;
import com.example.erp.domain.leavelog.entity.LeaveLog;
import com.example.erp.domain.leavelog.repository.LeaveLogRepository;
import com.example.erp.domain.user.entity.User;
import com.example.erp.global.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LeaveLogService {
    private final LeaveLogRepository leaveLogRepository;

    public LeaveLog findById(Long leaveNum) {
       return leaveLogRepository.findById(leaveNum).orElseThrow(() -> new CustomException(LeaveLogException.LEAVE_LOG_NOT_FOUND));
    }

    public Page<LeaveLogOfAdminDto> findLeaveLogOfAdminDto(Pageable pageable) {
        return leaveLogRepository.findLeaveLogOfAdminDto(pageable);
    }

    public Page<LeaveLogOfAdminDto> getLeaveLogsOfAdminByName(String name, Pageable pageable) {
        return leaveLogRepository.findLeaveLogOfAdminDtoByName(pageable,name);
    }

    @Transactional
    public void rejectLeaveLog(Long leaveNum) {
        LeaveLog leaveLog = findById(leaveNum);
        leaveLog.updateLeave(true, false);
    }

    @Transactional
    public void acceptLeave(Long leaveNum) {
        LeaveLog leaveLog = findById(leaveNum);
        leaveLog.updateLeave(true, true);
    }

    public Page<LeaveLogOfUserDto> findLeaveLogOfUserDtosByUser(Pageable pageable, User user) {
        return leaveLogRepository.findLeaveLogOfUserDtos(pageable, user);
    }

    @Transactional
    public void save(LeaveLog leaveLog) {
        leaveLogRepository.save(leaveLog);
    }
}
