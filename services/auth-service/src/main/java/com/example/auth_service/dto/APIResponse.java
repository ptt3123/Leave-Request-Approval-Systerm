package com.example.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class APIResponse<T> {
    private int code;
    private String detail;
    private T data;

    public static <T> APIResponse<T> success(T data) {
        return APIResponse.<T>builder()
                .code(200)
                .detail("Success")
                .data(data)
                .build();
    }

    public static <T> APIResponse<T> error(String message, int code) {
        return APIResponse.<T>builder()
                .code(code)
                .detail(message)
                .build();
    }

}
