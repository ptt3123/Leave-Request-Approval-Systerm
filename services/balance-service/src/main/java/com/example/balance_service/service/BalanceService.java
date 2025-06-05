package com.example.balance_service.service;

import com.example.balance_service.dto.BalanceDataDTO;

public interface BalanceService {

    void create(BalanceDataDTO balanceDataDTO);

    void updateBalance(BalanceDataDTO balanceDataDTO);

    int readBalance(Integer employeeId);

}
