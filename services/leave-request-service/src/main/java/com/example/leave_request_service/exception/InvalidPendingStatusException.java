package com.example.leave_request_service.exception;

public class InvalidPendingStatusException extends RuntimeException {
    public InvalidPendingStatusException() {
        super("Can't update Request's Status to PENDING.");
    }
}

