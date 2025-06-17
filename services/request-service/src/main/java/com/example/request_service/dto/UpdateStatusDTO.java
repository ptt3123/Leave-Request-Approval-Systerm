package com.example.request_service.dto;

import com.example.request_service.entity.Status;
import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class UpdateStatusDTO {

    @NotNull(message = "requestId is required")
    Integer requestId;

    @NotNull(message = "status is required")
    Status status;

}
