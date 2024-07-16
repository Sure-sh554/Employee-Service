package com.microservice.Employee.Servie.mapper;

import com.microservice.Employee.Servie.dto.EmployeeDto;
import com.microservice.Employee.Servie.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoEmployeeMapper {

    AutoEmployeeMapper MAPPER= Mappers.getMapper(AutoEmployeeMapper.class);

    EmployeeDto mapToEmployeeDto(EmployeeEntity employeeEntity);

    EmployeeEntity mapToEmployeeEntity(EmployeeDto employeeDto);
}
