package com.example.employee_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class EmployeeLoginDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 6, max = 15, message = "Username must not smaller than 6 and greater than 15 character!")
    String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 64, message = "Password must not smaller than 6 and greater than 64 character!")
    String password;

}
