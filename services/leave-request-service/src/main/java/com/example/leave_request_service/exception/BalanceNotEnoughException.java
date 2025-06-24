package com.example.leave_request_service.exception;

public class BalanceNotEnoughException extends RuntimeException {
    public BalanceNotEnoughException() {
        super("Balance not enough to submit request .");
    }
}
