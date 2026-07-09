package com.company.employee.service;

import com.company.employee.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService service;

    @BeforeEach
    void setup() {
        service = new EmployeeService();
        service.init();
    }

    @Test
    void shouldReturnDefaultEmployees() {
        assertEquals(3, service.getAllEmployees().size());
    }

    @Test
    void shouldAddEmployee() {

        Employee emp =
                new Employee(null,
                        "Robert",
                        "IT",
                        "robert@test.com",
                        55000);

        service.addEmployee(emp);

        assertEquals(4, service.getAllEmployees().size());

        assertNotNull(emp.getId());

    }

    @Test
    void shouldFindEmployeeById() {

        Employee employee =
                service.getEmployeeById(1L);

        assertNotNull(employee);

        assertEquals("John Smith", employee.getName());

    }

    @Test
    void shouldUpdateEmployee() {

        Employee employee =
                service.getEmployeeById(1L);

        employee.setDepartment("DevOps");

        service.updateEmployee(employee);

        assertEquals(
                "DevOps",
                service.getEmployeeById(1L).getDepartment());

    }

    @Test
    void shouldDeleteEmployee() {

        service.deleteEmployee(1L);

        assertEquals(2, service.getAllEmployees().size());

    }

}
