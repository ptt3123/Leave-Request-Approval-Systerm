package com.example.employee_service.controller;

import com.example.employee_service.dto.EmployeeLoginDTO;
import com.example.employee_service.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody EmployeeLoginDTO employeeLoginDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.login(
                        employeeLoginDTO.getUsername(),
                        employeeLoginDTO.getPassword()
                ));
    }
}
