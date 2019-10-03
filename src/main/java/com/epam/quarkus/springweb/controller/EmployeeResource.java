package com.epam.quarkus.springweb.controller;

import com.epam.quarkus.springweb.model.Employee;
import com.epam.quarkus.springweb.service.EmployeeDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeResource {

    @Autowired
    private EmployeeDaoService employeeDaoService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeDaoService.findAll());
    }

    @GetMapping("/{name}/{surname}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("name") String name, @PathVariable("surname") String surname) {

        Optional<Employee> employee = employeeDaoService.findByNameAndSurname(name, surname);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public void saveEmployee(Employee employee) {
        employeeDaoService.save(employee);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity deleteEmployee(Employee employee) {
        employeeDaoService.delete(employee);
        return ResponseEntity.noContent().build();
    }
}