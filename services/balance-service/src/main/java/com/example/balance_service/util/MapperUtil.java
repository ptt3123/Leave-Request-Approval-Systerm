package com.example.balance_service.util;

import com.example.balance_service.dto.BalanceDataDTO;
import com.example.balance_service.entity.Balance;

public class MapperUtil {
    public static Balance balanceDataDTOToBalance(BalanceDataDTO balanceDataDTO){
        return Balance.builder()
                .balance(balanceDataDTO.getBalance())
                .employee_id(balanceDataDTO.getEmployee_id())
                .build();
    }
}
