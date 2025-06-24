package com.example.leave_request_service.controller;

import com.example.leave_request_service.dto.Type;
import com.example.leave_request_service.exception.BalanceNotEnoughException;
import com.example.leave_request_service.exception.DownstreamServiceException;
import com.example.leave_request_service.exception.InvalidPendingStatusException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class LeaveRequestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleEnumConvertError(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException &&
                ((InvalidFormatException) ex.getCause()).getTargetType().isEnum()) {

            return ResponseEntity.badRequest().body("Loại nghỉ phép không hợp lệ. Các loại hợp lệ: " +
                    Arrays.toString(Type.values()));
        }

        return ResponseEntity.badRequest().body("Dữ liệu không hợp lệ: " + ex.getMessage());
    }

    @ExceptionHandler(BalanceNotEnoughException.class)
    public ResponseEntity<String> handleBalanceNotEnoughException(BalanceNotEnoughException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidPendingStatusException.class)
    public ResponseEntity<String> handleInvalidPendingStatusException(InvalidPendingStatusException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DownstreamServiceException.class)
    public ResponseEntity<String> handleDownstreamServiceError(DownstreamServiceException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getBody());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        log.error("Lỗi hệ thống", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Hệ thống đang gặp lỗi. Vui lòng thử lại sau!!!");
    }

}
