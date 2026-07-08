package com.company.employee.model;

public class Employee {

    private Long id;
    private String name;
    private String department;
    private String email;
    private double salary;

    public Employee() {
    }

    public Employee(Long id, String name, String department, String email, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.email = email;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
