package com.microservice.Employee.Servie.service.Impl;

import com.microservice.Employee.Servie.dto.EmployeeDto;
import com.microservice.Employee.Servie.entity.EmployeeEntity;
import com.microservice.Employee.Servie.exception.EmailAlreadyExistsException;
import com.microservice.Employee.Servie.exception.ResourceNotFoundException;
import com.microservice.Employee.Servie.mapper.AutoEmployeeMapper;
import com.microservice.Employee.Servie.repository.EmployeeRepository;
import com.microservice.Employee.Servie.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
//        EmployeeEntity employeeEntity = new EmployeeEntity(
//                employeeDto.getId(),
//                employeeDto.getFirstName(),
//                employeeDto.getLastName(),
//                employeeDto.getEmail()
//        );
//        EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class);

        Optional<EmployeeEntity> employeeEntityEmail = employeeRepository.findByEmail(employeeDto.getEmail());
        if (employeeEntityEmail.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exists for the Employee");

        }
        EmployeeEntity employeeEntity = AutoEmployeeMapper.MAPPER.mapToEmployeeEntity(employeeDto);

        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
//        EmployeeDto savedEmployeeDto = new EmployeeDto(
//                savedEmployee.getId(),
//                savedEmployee.getFirstName(),
//                savedEmployee.getLastName(),
//                savedEmployee.getEmail()
//        );

//        EmployeeDto savedEmployeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);
        EmployeeDto savedEmployeeDto = AutoEmployeeMapper.MAPPER.mapToEmployeeDto(savedEmployee);


        return savedEmployeeDto;
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("EMPLOYEE", "code", employeeId));

//        EmployeeDto employeeDto = new EmployeeDto(
//                employeeEntity.getId(),
//                employeeEntity.getFirstName(),
//                employeeEntity.getLastName(),
//                employeeEntity.getEmail()
//        );
//        return modelMapper.map(employeeEntity,EmployeeDto.class);
        return AutoEmployeeMapper.MAPPER.mapToEmployeeDto(employeeEntity);
    }


}
