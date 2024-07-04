package com.microservice.Employee.Servie.repository;

import com.microservice.Employee.Servie.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    Optional<EmployeeEntity> findByEmail(String email);

}
