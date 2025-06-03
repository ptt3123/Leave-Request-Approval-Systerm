package com.example.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class APIResponse<T> {
    private int code;
    private String detail;
    private T data;
}
