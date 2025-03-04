package com.example.erp.domain.leavelog.dto;

//db 날짜와 호환되는 date 라이브러리 임포트.


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 *
 *  휴가 정보를 User 에게 보내기위한 dto
 * */

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveLogOfUserDto {

    private LocalDate requestDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean acceptanceStatus = false;



}
