package com.epam.quarkus.springweb.controller;

import com.epam.quarkus.springweb.model.Employee;
import com.epam.quarkus.springweb.service.EmployeeDaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    private Employee firstEmployee;

    private Employee secondEmployee;

    @Autowired
    private EmployeeDaoService employeeDaoService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        expectedEmployees = new ArrayList<>();
        employeeDaoService.deleteAll();
        firstEmployee = Employee.builder()
                .name("firstName")
                .surname("firstSurname")
                .position("testPosition")
                .build();

        secondEmployee = Employee.builder()
                .name("secondName")
                .surname("secondSurname")
                .position("testPosition")
                .build();
    }

    @Test
    public void getAllEmployeesTest() {
        //Given
        expectedEmployees.add(firstEmployee);
        expectedEmployees.add(secondEmployee);
        employeeDaoService.saveAll(expectedEmployees);
        //When
        List actualEmployees = given()
                .when()
                .get("/employee")
                .then()
                .statusCode(200)
                .extract().as(objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        //Then
        assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    public void saveEmployeeTest() throws JsonProcessingException {
        //Given
        expectedEmployees.add(firstEmployee);
        //When
        given()
                .when()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(firstEmployee))
                .post("/employee")
                .then()
                .statusCode(204);
        List<Employee> actualEmployees = given()
                .when()
                .get("/employee")
                .then()
                .statusCode(200)
                .extract().as(objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        //Then
        Employee actualEmployee = actualEmployees.get(0);
        assertEquals(firstEmployee.getName(), actualEmployee.getName());
        assertEquals(firstEmployee.getSurname(), actualEmployee.getSurname());
    }

    @Test
    public void updateEmployeeTest() throws JsonProcessingException {
        //Given
        employeeDaoService.save(firstEmployee);
        firstEmployee.setPosition("updatedPosition");
        expectedEmployees.add(firstEmployee);
        //When
        given()
                .when()
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(firstEmployee))
                .post("/employee")
                .then()
                .statusCode(204);
        List<Employee> actualEmployees = given()
                .when()
                .get("/employee")
                .then()
                .statusCode(200)
                .extract().as(objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        //Then
        Employee actualEmployee = actualEmployees.get(0);
        assertEquals(firstEmployee.getName(), actualEmployee.getName());
        assertEquals(firstEmployee.getSurname(), actualEmployee.getSurname());
        assertEquals(firstEmployee.getPosition(), actualEmployee.getPosition());
    }
}
