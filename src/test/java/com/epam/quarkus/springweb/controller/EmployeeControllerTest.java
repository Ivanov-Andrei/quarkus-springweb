package com.epam.quarkus.springweb.controller;

import com.epam.quarkus.springweb.model.Employee;
import com.epam.quarkus.springweb.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@RegisterForReflection
public class EmployeeControllerTest {

    private List<Employee> expectedEmployees;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        Employee employee = Employee.builder()
                .id(1L)
                .name("testName")
                .surName("testSurname")
                .position("testPosition")
                .build();
        employeeRepository.save(employee);
        expectedEmployees = new ArrayList<>();
        expectedEmployees.add(employee);
    }

    @Test
    public void testHelloEndpoint() {
        List actualEmployees = given()
                .when().get("/employee")
                .then()
                .statusCode(200)
                .extract().as(objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        assertEquals(expectedEmployees, actualEmployees);
    }
}
