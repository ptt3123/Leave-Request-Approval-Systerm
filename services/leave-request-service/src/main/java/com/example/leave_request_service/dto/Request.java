package com.example.leave_request_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Request {

    private Integer id;

    private LocalDate create_at;

    private LocalDate start_at;

    private LocalDate end_at;

    private Integer employee_id;

    private LocalDate update_at;

    private Type type;

    private String detail;

    private Status status;

}
