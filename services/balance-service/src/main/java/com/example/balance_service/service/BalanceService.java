package com.example.balance_service.service;

import com.example.balance_service.dto.BalanceDataDTO;
import com.example.balance_service.dto.BalanceUpdateDTO;

public interface BalanceService {


    void create(BalanceDataDTO balanceDataDTO);

    void updateBalance(BalanceUpdateDTO balanceUpdateDTO);

    int readBalance(Integer employeeId);

}
