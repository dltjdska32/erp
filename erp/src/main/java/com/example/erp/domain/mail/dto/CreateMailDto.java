package com.example.erp.domain.mail.dto;

public record CreateMailDto (
        String email,
        String title,
        String contents
)
{
}
