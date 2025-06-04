package com.example.employee_service.util;

import com.example.employee_service.dto.EmployeeRegisterDTO;
import com.example.employee_service.entity.Employee;

public class MapperUtil {
    public static Employee employeeRegisterDTOToEmployee(EmployeeRegisterDTO employeeRegisterDTO){
        return Employee.builder()
                .name(employeeRegisterDTO.getName())
                .username(employeeRegisterDTO.getUsername())
                .password(employeeRegisterDTO.getPassword())
                .DOB(employeeRegisterDTO.getDOB())
                .phone_number(employeeRegisterDTO.getPhone_number())
                .email(employeeRegisterDTO.getEmail())
                .address(employeeRegisterDTO.getAddress())
                .salary(employeeRegisterDTO.getSalary())
                .is_manager(employeeRegisterDTO.getIs_manager())
                .team_id(employeeRegisterDTO.getTeam_id())
                .build();
    }
}
