package com.example.employee_service.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.time.LocalDate;

@Value
public class EmployeeRegisterDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must not smaller than 3 and greater than 50 character!")
    String name;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 15, message = "Username must not smaller than 4 and greater than 15 character!")
    String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 64, message = "Password must not smaller than 6 and greater than 64 character!")
    String password;

    @NotBlank(message = "Confirm Password is required")
    String confirm_password;

    @NotNull(message = "Date of birth is required")
    LocalDate DOB;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    String phone_number;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Size(min = 10, max = 50, message = "Email must not smaller than 10 and greater than 50 character!")
    String email;

    @NotBlank(message = "Address is required")
    @Size(min = 10, max = 100, message = "Address must not smaller than 10 and greater than 100 character!")
    String address;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "1.0", message = "Salary must be at least 1.0")
    Float salary;

    @NotNull(message = "Team ID is required")
    @Min(value = 1, message = "Team Id not smaller than 1!")
    Integer team_id;

    @NotNull(message = "Is Manager is required")
    Boolean is_manager;

    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordConfirmed() {
        if (password == null || confirm_password == null) return false;
        return password.equals(confirm_password);
    }

}

