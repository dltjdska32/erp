package com.example.erp.domain.sendmail.repository;

import com.example.erp.domain.sendmail.dto.SendMailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SendMailRepositoryCustom {
    Page<SendMailDto> findSendMailDtosByUserNum(Pageable pageable, Long userNum);

    Page<SendMailDto> findSendMailDtosByUserNumAndTitle(Pageable pageable, Long userNum, String title);
}
