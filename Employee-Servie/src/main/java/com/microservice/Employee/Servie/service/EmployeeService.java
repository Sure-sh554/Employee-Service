package com.microservice.Employee.Servie.service;

import com.microservice.Employee.Servie.dto.APIResponseDto;
import com.microservice.Employee.Servie.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    APIResponseDto getEmployeeById(Long employeeId);
}
