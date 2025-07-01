package com.example.employee_service.service.impl;

import com.example.employee_service.client.RCToAuthService;
import com.example.employee_service.dto.EmployeeLoginDTO;
import com.example.employee_service.dto.EmployeeRegisterDTO;
import com.example.employee_service.dto.JWTData;
import com.example.employee_service.dto.UniqueInfoDTO;
import com.example.employee_service.entity.Employee;
import com.example.employee_service.exception.DuplicatedInformationException;
import com.example.employee_service.exception.EmployeeNotFoundException;
import com.example.employee_service.repository.EmployeeRepository;
import com.example.employee_service.service.EmployeeService;
import com.example.employee_service.util.MapperUtil;
import com.example.employee_service.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RCToAuthService rcToAuthService;

    @Override
    public String login(EmployeeLoginDTO employeeLoginDTO) {
        Optional<Employee> employee = employeeRepository.findByUsername(
                employeeLoginDTO.getUsername());

        if(employee.isEmpty()){
            throw new EmployeeNotFoundException();
        }

        Employee e = employee.get();
        if(!PasswordUtil.verifyPassword(employeeLoginDTO.getPassword(), e.getPassword())){
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

    @Override
    public void register(EmployeeRegisterDTO employeeRegisterDTO) {
        try {
            employeeRepository.save(MapperUtil.employeeRegisterDTOToEmployee(employeeRegisterDTO));

        } catch (DataIntegrityViolationException ex){
            String username = employeeRegisterDTO.getUsername();
            String phone_number = employeeRegisterDTO.getPhone_number();
            String email = employeeRegisterDTO.getEmail();

            List<UniqueInfoDTO> list = employeeRepository.findUniqueInformation(
                    username, phone_number, email);

            StringBuilder errorMessage = new StringBuilder();
            int cnt = 0;
            int countOfUniqueInformation = 3;

            for (UniqueInfoDTO dto: list){
                if (dto.getUsername().equals(username)) {
                    errorMessage.append("Username: Username has been used; ");
                    cnt += 1;
                    if (cnt == countOfUniqueInformation) break;
                }
                if (dto.getPhone_number().equals(phone_number)) {
                    errorMessage.append("Phone number: Phone number has been used; ");
                    cnt += 1;
                    if (cnt == countOfUniqueInformation) break;
                }
                if (dto.getEmail().equals(email)) {
                    errorMessage.append("Email: Email has been used; ");
                    cnt += 1;
                    if (cnt == countOfUniqueInformation) break;
                }
            }

            throw new DuplicatedInformationException(errorMessage.toString().trim());
        }
    }

    @Override
    public String readEmployeeEmail(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if(employee.isEmpty()){
            throw new EmployeeNotFoundException();
        }

        return employee.get().getEmail();
    }

    @Override
    public List<Integer> readListEmployeeId(Integer managerId) {
        return employeeRepository.findIdsByManagerId(managerId);
    }

}