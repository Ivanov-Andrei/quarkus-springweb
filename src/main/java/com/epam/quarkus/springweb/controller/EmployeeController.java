package com.epam.quarkus.springweb.controller;

import com.epam.quarkus.springweb.model.Employee;
import com.epam.quarkus.springweb.search.EmployeeSearchService;
import com.epam.quarkus.springweb.search.model.SearchCriteria;
import com.epam.quarkus.springweb.service.EmployeeDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeDaoService employeeDaoService;

    @Autowired
    private EmployeeSearchService employeeSearchService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeDaoService.findAll();
    }

    @GetMapping
    public List<Employee> getEmployee(SearchCriteria searchCriteria) {
        return employeeSearchService.search(searchCriteria);
    }

    @PostMapping
    public void saveEmployee(Employee employee) {
        employeeDaoService.save(employee);
    }
}
