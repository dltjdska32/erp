package com.example.erp.domain.sendmail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class SendMailDto {

    private Long sendMailNum;
    private String receiverName;
    private String title;
    private LocalDate receivedDate;

}
