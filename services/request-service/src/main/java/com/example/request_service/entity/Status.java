package com.example.request_service.entity;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("Chờ duyệt"),
    APPROVED("Đã chấp nhận"),
    REJECTED("Đã từ chối");

    private final String description;

    Status(String description) {
        this.description = description;
    }
}
