package com.example.request_service.dto;

import com.example.request_service.entity.Type;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDate;

@Value
public class RequestSubmitDTO {

    @NotNull(message = "start_at is required")
    LocalDate start_at;

    @NotNull(message = "end_at is required")
    LocalDate end_at;

    @NotNull(message = "employee_id is required")
    Integer employee_id;

    @NotNull(message = "type is required")
    Type type;

    @NotNull(message = "detail is required")
    String detail;

}
