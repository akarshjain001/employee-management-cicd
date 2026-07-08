package com.company.employee.service;

import com.company.employee.model.Employee;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();
    private Long nextId = 1L;

    @PostConstruct
    public void init() {
        employees.add(new Employee(nextId++, "John Smith", "IT", "john@company.com", 65000));
        employees.add(new Employee(nextId++, "Alice Johnson", "HR", "alice@company.com", 70000));
        employees.add(new Employee(nextId++, "David Miller", "Finance", "david@company.com", 72000));
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employee.setId(nextId++);
        employees.add(employee);
    }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee =
                employees.stream()
                         .filter(e -> e.getId().equals(id))
                         .findFirst();

        return employee.orElse(null);
    }

    public void updateEmployee(Employee employee) {

        Employee existing = getEmployeeById(employee.getId());

        if (existing != null) {
            existing.setName(employee.getName());
            existing.setDepartment(employee.getDepartment());
            existing.setEmail(employee.getEmail());
            existing.setSalary(employee.getSalary());
        }
    }

    public void deleteEmployee(Long id) {
        employees.removeIf(employee -> employee.getId().equals(id));
    }

}
