package com.aivanov.quarkus.springweb.controller;

import com.aivanov.quarkus.springweb.model.Employee;
import com.aivanov.quarkus.springweb.service.EmployeeDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity saveEmployee(@RequestBody Employee employee) {
        employeeDaoService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @DeleteMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity deleteEmployee(@RequestBody Employee employee) {
        employeeDaoService.delete(employee);
        return ResponseEntity.noContent().build();
    }
}
