package com.company.employee.controller;

import com.company.employee.model.Employee;
import com.company.employee.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees";
    }

    @GetMapping("/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee) {

        if (employee.getId() == null) {
            employeeService.addEmployee(employee);
        } else {
            employeeService.updateEmployee(employee);
        }

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {

        model.addAttribute("employee", employeeService.getEmployeeById(id));

        return "edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return "redirect:/";
    }
}
