package com.example.erp.domain.user.entity;

public enum Role {
    USER("일반 유저"),
    ADMIN("관리자");

    private final String description;
    public static Role fromString(String role) {
        return Role.valueOf(role.toUpperCase());
    }
    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
