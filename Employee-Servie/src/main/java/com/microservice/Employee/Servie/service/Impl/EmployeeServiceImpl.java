package com.microservice.Employee.Servie.service.Impl;

import com.microservice.Employee.Servie.dto.APIResponseDto;
import com.microservice.Employee.Servie.dto.EmployeeDto;
import com.microservice.Employee.Servie.dto.DepartmentDto;
import com.microservice.Employee.Servie.entity.EmployeeEntity;
import com.microservice.Employee.Servie.exception.EmailAlreadyExistsException;
import com.microservice.Employee.Servie.exception.ResourceNotFoundException;
import com.microservice.Employee.Servie.mapper.AutoEmployeeMapper;
import com.microservice.Employee.Servie.repository.EmployeeRepository;
import com.microservice.Employee.Servie.service.APIClient;
import com.microservice.Employee.Servie.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    //    private RestTemplate restTemplate;
    //private WebClient webClient;
    private APIClient apiClient;


    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
//        EmployeeEntity employeeEntity = new EmployeeEntity(
//                employeeDto.getId(),
//                employeeDto.getFirstName(),
//                employeeDto.getLastName(),
//                employeeDto.getEmail()
//        );
        //     EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class);

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
    public APIResponseDto getEmployeeById(Long employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("EMPLOYEE", "code", employeeId));
//        ResponseEntity<DepartmentDto> departmentDtoResponseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments"
//                + employeeEntity
//                .getDepartmentCode(), DepartmentDto.class);
//        DepartmentDto departmentDto = departmentDtoResponseEntity.getBody();
//       DepartmentDto departmentDto =webClient.get()
//                .uri("http://localhost:8080/api/departments" + employeeEntity.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();
        DepartmentDto departmentDto = apiClient.getDepartmentByCode(employeeEntity.getDepartmentCode());

//        EmployeeDto employeeDto = new EmployeeDto(
//                employeeEntity.getId(),
//                employeeEntity.getFirstName(),
//                employeeEntity.getLastName(),
//                employeeEntity.getEmail()
//        );

//        return modelMapper.map(employeeEntity,EmployeeDto.class);
        APIResponseDto apiResponseDto = new APIResponseDto();
        // apiResponseDto.setEmployee(modelMapper.map(employeeEntity,EmployeeDto.class));
        apiResponseDto.setEmployee(AutoEmployeeMapper.MAPPER.mapToEmployeeDto(employeeEntity));
        apiResponseDto.setDepartment(departmentDto);
        return apiResponseDto;
    }


}
