package com.example.employee_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class EmployeeLoginDTO {

    @NotBlank(message = "Username is required")
    String username;

    @NotBlank(message = "Password is required")
    String password;

}
