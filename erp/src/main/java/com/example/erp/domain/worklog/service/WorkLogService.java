package com.example.erp.domain.worklog.service;

import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.worklog.dto.UserWorkLogDto;
import com.example.erp.domain.worklog.dto.resp.AttendanceRespDto;
import com.example.erp.domain.worklog.entity.Status;
import com.example.erp.domain.worklog.entity.WorkLog;
import com.example.erp.domain.worklog.exception.WorkLogException;
import com.example.erp.domain.worklog.repository.WorkLogRepository;
import com.example.erp.global.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkLogService {
    private final WorkLogRepository workLogRepository;

    public Page<UserWorkLogDto> findUserWorkLogByUserNum(Pageable pageable, Long userNum) {
        return workLogRepository.findUserWorkLogByUserNum(pageable, userNum);
    }

    public Optional<WorkLog> findWorkLogByUserAndDate(User user, LocalDate today) {
         return workLogRepository.findByUserAndWorkDate(user, today);
    }




    @Transactional
    public void updateWorkLog(User user, LocalDate today,LocalTime currentTime, Status status) {
        WorkLog workLog = workLogRepository.findByUserAndWorkDate(user, today).orElseThrow(() -> new CustomException(WorkLogException.WORK_LOG_NOT_FOUND));

        workLog.updateWorkLog(currentTime, status);
    }

    @Transactional
    public void setGetOffWork(User user, LocalDate today, LocalTime currentTime, Status status) {
        WorkLog workLog = workLogRepository.findByUserAndWorkDate(user, today).orElseThrow(() -> new CustomException(WorkLogException.WORK_LOG_NOT_FOUND));

        if(status == null ) {
            workLog.setEndTime(currentTime);
            return;
        }

        workLog.setGetOffWork(currentTime, status);
    }

    @Transactional
    public void save(WorkLog worklog) {
        workLogRepository.save(worklog);
    }
}
