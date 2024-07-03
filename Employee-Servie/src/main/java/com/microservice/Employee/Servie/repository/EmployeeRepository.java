package com.microservice.Employee.Servie.repository;

import com.microservice.Employee.Servie.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {

}
