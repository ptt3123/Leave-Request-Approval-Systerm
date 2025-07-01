package com.example.balance_service.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class BalanceUpdateDTO {

    @NotNull(message = "Balance is required")
    Integer balance;

    @NotNull(message = "Employee Id is required")
    Integer employee_id;

}