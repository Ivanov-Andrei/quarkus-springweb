package com.epam.quarkus.springweb.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "employeeGenerator")
    private Long id;

    private String name;

    private String surName;

    private String position;
}
