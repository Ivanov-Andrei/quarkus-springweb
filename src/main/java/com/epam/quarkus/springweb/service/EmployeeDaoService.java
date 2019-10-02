package com.epam.quarkus.springweb.service;

import com.epam.quarkus.springweb.model.Employee;

import java.util.List;

public interface EmployeeDaoService {

    void save(Employee employee);

    List<Employee> findAll();

    void deleteAll();

    void saveAll(List<Employee> employees);
}
