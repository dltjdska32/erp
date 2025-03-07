package com.example.erp.domain.mail.entity;

public enum MailType {
    RECEIVED("받은 메일함"),
    SEND("보내는 메일함");

    private final String description;

    MailType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
