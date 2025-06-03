package com.example.employee_service.service.impl;

import com.example.employee_service.client.RCToAuthService;
import com.example.employee_service.dto.JWTData;
import com.example.employee_service.entity.Employee;
import com.example.employee_service.exception.EmployeeNotFoundException;
import com.example.employee_service.repository.EmployeeRepository;
import com.example.employee_service.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PasswordService passwordService;

    private final RCToAuthService rcToAuthService;

    public EmployeeServiceImpl(
            PasswordService passwordService,
            EmployeeRepository employeeRepository,
            RCToAuthService rcToAuthService
    ){
        this.employeeRepository = employeeRepository;
        this.passwordService = passwordService;
        this.rcToAuthService = rcToAuthService;
    }

    @Override
    public String login(String username, String password) {
        Optional<Employee> employee = employeeRepository.findByUsername(username);
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException();
        }

        Employee e = employee.get();
        if(!passwordService.verifyPassword(password, e.getPassword())){
            throw new EmployeeNotFoundException();
        }

        return rcToAuthService.generateJWT(
                JWTData.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .is_manager(e.getIs_manager())
                        .build()
        ).getBody();
    }
}
