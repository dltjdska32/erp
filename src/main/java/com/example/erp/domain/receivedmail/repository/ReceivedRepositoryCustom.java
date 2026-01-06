package com.example.erp.domain.receivedmail.repository;

import com.example.erp.domain.receivedmail.dto.ReceivedMailDto;
import com.example.erp.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReceivedRepositoryCustom {
    Page<ReceivedMailDto> findReceivedMails(Pageable pageable, User user);

    Page<ReceivedMailDto> findReceivedMailsByTitle(Pageable pageable, User user, String title);
}
