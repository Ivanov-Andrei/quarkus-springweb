package com.epam.quarkus.springweb.service.impl;

import com.epam.quarkus.springweb.model.Employee;
import com.epam.quarkus.springweb.repository.EmployeeRepository;
import com.epam.quarkus.springweb.service.EmployeeDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeDaoServiceImpl implements EmployeeDaoService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public void save(Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.findByNameAndSurname(employee.getName(), employee.getSurname());
        if (existingEmployee.isPresent()) {
            Employee entity = existingEmployee.get();
            entity.setName(employee.getName());
            entity.setSurname(employee.getSurname());
            entity.setPosition(employee.getPosition());
            employeeRepository.save(entity);
        } else {
            employeeRepository.save(employee);
        }
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
