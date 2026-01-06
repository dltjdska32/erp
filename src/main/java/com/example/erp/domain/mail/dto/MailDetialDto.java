package com.example.erp.domain.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MailDetialDto {
    private String title;
    private String content;
    private String senderEmail;
}

