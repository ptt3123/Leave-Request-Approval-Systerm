package com.example.leave_request_service.dto;

import lombok.Getter;

@Getter
public enum Type {
    ANNUAL("Nghỉ phép năm", true, true),
    SICK("Nghỉ ốm", false, true),
    UNPAID("Nghỉ không lương", true, false),
    MATERNITY("Nghỉ thai sản", false, true),
    PATERNITY("Nghỉ hưởng chế độ cha", false, true),
    OTHER("Khác", true, false);

    private final String description;
    private final boolean deductedFromAnnualLeave;
    private final boolean paid;

    Type(String description, boolean deductedFromAnnualLeave, boolean paid) {
        this.description = description;
        this.deductedFromAnnualLeave = deductedFromAnnualLeave;
        this.paid = paid;
    }
}
