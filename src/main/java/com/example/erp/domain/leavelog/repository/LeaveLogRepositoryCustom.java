package com.example.erp.domain.leavelog.repository;

import com.example.erp.domain.leavelog.dto.LeaveLogOfAdminDto;
import com.example.erp.domain.leavelog.dto.LeaveLogOfUserDto;
import com.example.erp.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeaveLogRepositoryCustom {
    Page<LeaveLogOfAdminDto> findLeaveLogOfAdminDto(Pageable pageable);

    Page<LeaveLogOfAdminDto> findLeaveLogOfAdminDtoByName(Pageable pageable, String name);

    Page<LeaveLogOfUserDto> findLeaveLogOfUserDtos(Pageable pageable, User user);
}
