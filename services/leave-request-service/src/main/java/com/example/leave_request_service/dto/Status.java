package com.example.leave_request_service.dto;

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
