package com.aivanov.quarkus.springweb.repository;

import com.aivanov.quarkus.springweb.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByNameAndSurname(String name, String surname);
}
