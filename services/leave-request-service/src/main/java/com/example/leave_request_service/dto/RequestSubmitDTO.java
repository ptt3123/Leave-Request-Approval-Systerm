package com.example.leave_request_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestSubmitDTO {

    @NotNull(message = "start_at is required")
    LocalDate start_at;

    @NotNull(message = "end_at is required")
    LocalDate end_at;

    Integer employee_id;

    @NotNull(message = "type is required")
    Type type;

    @NotBlank(message = "detail is required")
    @Size(min = 3, max = 150, message = "Detail must not smaller than 3 and greater than 50 character!")
    String detail;

    @AssertTrue(message = "Start date must be before end date")
    public boolean isValidDateRange(){
        if (start_at == null || end_at == null) return false;
        return start_at.isBefore(end_at);
    }

}
