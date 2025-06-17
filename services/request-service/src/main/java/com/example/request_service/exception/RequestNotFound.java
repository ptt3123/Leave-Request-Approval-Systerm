package com.example.request_service.exception;

public class RequestNotFound extends RuntimeException {
    public RequestNotFound() {
        super("Request not found.");
    }
}