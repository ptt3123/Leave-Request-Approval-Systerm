package com.example.employee_service.service;

import com.example.employee_service.dto.EmployeeLoginDTO;
import com.example.employee_service.dto.EmployeeRegisterDTO;

import java.util.List;

public interface EmployeeService {

    String login(EmployeeLoginDTO employeeLoginDTO);

    void register(EmployeeRegisterDTO employeeRegisterDTO);

    String readEmployeeEmail(Integer employeeId);

    List<Integer> readListEmployeeId(Integer managerId);
}
