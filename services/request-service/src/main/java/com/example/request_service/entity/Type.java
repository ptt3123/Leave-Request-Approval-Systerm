package com.example.request_service.entity;

import lombok.Getter;

@Getter
public enum Type {
    ANNUAL("Nghỉ phép năm"),
    SICK("Nghỉ ốm"),
    UNPAID("Nghỉ không lương"),
    MATERNITY("Nghỉ thai sản"),
    PATERNITY("Nghỉ hưởng chế độ cha"),
    OTHER("Khác");

    private final String description;

    Type(String description) {
        this.description = description;
    }
}
