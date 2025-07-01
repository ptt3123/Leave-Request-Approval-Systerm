package com.example.balance_service.service.impl;

import com.example.balance_service.dto.BalanceDataDTO;
import com.example.balance_service.dto.BalanceUpdateDTO;
import com.example.balance_service.exception.*;
import com.example.balance_service.repository.BalanceRepository;
import com.example.balance_service.service.BalanceService;
import com.example.balance_service.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    @Override
    public void create(BalanceDataDTO balanceDataDTO) {
        if (balanceRepository.existsBalanceThisYearByEmployeeIdRaw(balanceDataDTO.getEmployee_id()) == 1L){
            throw new BalanceOfEmployeeExistedException();
        }

        balanceRepository.save(MapperUtil.balanceDataDTOToBalance(balanceDataDTO));
    }

    @Override
    public void updateBalance(BalanceUpdateDTO balanceUpdateDTO) {
        int flag = balanceRepository.updateLeaveBalanceThisYear(
                balanceUpdateDTO.getEmployee_id(),
                balanceUpdateDTO.getBalance());

        if (flag == 0) {
            throw new BalanceNotFoundException();
        }
    }

    @Override
    public int readBalance(Integer employeeId) {
        Integer balance = balanceRepository
                .findCurrentYearBalanceByEmployeeId(employeeId).orElse(null);

        if (balance == null){
            throw new BalanceNotFoundException();
        }

        return balance;
    }

}
