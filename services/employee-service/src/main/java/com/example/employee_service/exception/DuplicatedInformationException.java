package com.example.employee_service.exception;

public class DuplicatedInformationException extends RuntimeException {
    public DuplicatedInformationException(String ex) {
        super(ex);
    }
}
