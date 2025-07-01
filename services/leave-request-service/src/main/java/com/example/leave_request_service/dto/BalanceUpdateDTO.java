package com.example.leave_request_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceUpdateDTO {
    Integer employeeId;
    Integer newBalance;
}
