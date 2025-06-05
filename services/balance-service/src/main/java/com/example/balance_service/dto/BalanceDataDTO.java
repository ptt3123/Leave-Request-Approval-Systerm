package com.example.balance_service.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class BalanceDataDTO {

    @NotNull(message = "Balance is required")
    @Min(value = 15, message = "Balance must not be smaller than 1/2 months (15 days)")
    @Max(value = 45, message = "Balance must not be greater than 3/2 months (45 days)")
    Integer balance;

    @NotNull(message = "Employee Id is required")
    @Min(value = 1, message = "Employee Id must not be smaller than 1")
    Integer employee_id;

}
