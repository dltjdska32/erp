package com.example.erp.domain.receivedmail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedMailDto {

    private Long receivedMailNum;
    private String senderName;
    private String title;
    private LocalDate receivedDate;

}
