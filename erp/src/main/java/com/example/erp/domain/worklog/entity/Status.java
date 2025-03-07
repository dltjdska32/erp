package com.example.erp.domain.worklog.entity;

public enum Status {
    ABSENCE("결근"),
    TARDINESS("지각"),
    ATTENDANCE("출근"),
    LEAVEPREV("조퇴"),
    LEAVE("휴가");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
