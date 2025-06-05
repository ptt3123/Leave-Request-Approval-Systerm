package com.example.balance_service.exception;

public class BalanceNotFoundException extends RuntimeException {
    public BalanceNotFoundException() {
        super("Thông tin số ngày nghỉ hợp lệ trong năm của nhân viên không tồn tại!");
    }
}