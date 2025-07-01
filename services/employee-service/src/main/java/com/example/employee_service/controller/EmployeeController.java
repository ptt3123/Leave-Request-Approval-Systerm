package com.example.employee_service.controller;

import com.example.employee_service.dto.EmployeeLoginDTO;
import com.example.employee_service.dto.EmployeeRegisterDTO;
import com.example.employee_service.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/e")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody @Valid EmployeeLoginDTO employeeLoginDTO){

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(employeeService.login(employeeLoginDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid EmployeeRegisterDTO employeeRegisterDTO){

        employeeService.register(employeeRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/read-employee-email/{employeeId}")
    public ResponseEntity<String> readEmployeeEmail(@PathVariable Integer employeeId){
        return ResponseEntity.ok(employeeService.readEmployeeEmail(employeeId));
    }

    @GetMapping("/read-list-employee-id/{managerId}")
    public ResponseEntity<List<Integer>> readListEmployeeId(@PathVariable Integer managerId){
        return ResponseEntity.ok(employeeService.readListEmployeeId(managerId));
    }

}
