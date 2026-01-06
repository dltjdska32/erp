package com.example.erp.domain.worklog.repository;

import com.example.erp.domain.worklog.dto.UserWorkLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkLogRepositoryCustom {
    Page<UserWorkLogDto> findUserWorkLogByUserNum(Pageable pageable, Long userNum);
}
