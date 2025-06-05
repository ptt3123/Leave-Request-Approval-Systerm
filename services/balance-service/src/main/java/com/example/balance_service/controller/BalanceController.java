package com.example.balance_service.controller;

import com.example.balance_service.dto.BalanceDataDTO;
import com.example.balance_service.service.BalanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/b")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @PostMapping("/create-balance")
    public ResponseEntity<Void> create(@RequestBody @Valid BalanceDataDTO balanceDataDTO){

        balanceService.create(balanceDataDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/update-balance")
    public ResponseEntity<Void> updateBalance(@RequestBody @Valid BalanceDataDTO balanceDataDTO){

        balanceService.updateBalance(balanceDataDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/get-balance")
    public ResponseEntity<Integer> readBalance(@RequestHeader("X-User-Id") String userId){

        Integer uid = Integer.parseInt(userId);
        return ResponseEntity.status(HttpStatus.OK).body(balanceService.readBalance(uid));
    }
}
