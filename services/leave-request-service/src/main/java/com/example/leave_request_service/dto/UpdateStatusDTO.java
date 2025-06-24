package com.example.leave_request_service.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class UpdateStatusDTO {

    @NotNull(message = "requestId is required")
    @Min(value = 1, message = "Request Id must not be smaller than 1")
    Integer requestId;

    @NotNull(message = "status is required")
    Status status;

}
