package com.example.balance_service.exception;

public class BalanceOfEmployeeExistedException extends RuntimeException {
    public BalanceOfEmployeeExistedException() {
        super("Thông tin về số ngày nghỉ hợp lệ trong năm của nhân viên đã tồn tại!");
    }
}
