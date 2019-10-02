package com.epam.quarkus.springweb.search;

import com.epam.quarkus.springweb.model.Employee;
import com.epam.quarkus.springweb.search.model.SearchCriteria;

import java.util.List;

public interface EmployeeSearchService {

    List<Employee> search(SearchCriteria searchCriteria);

}
