package com.company.employee.controller;

import com.company.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void shouldLoadHomePage() throws Exception {

        mockMvc.perform(get("/"))

                .andExpect(status().isOk())

                .andExpect(view().name("employees"));

    }

    @Test
    void shouldLoadAddEmployeePage() throws Exception {

        mockMvc.perform(get("/add"))

                .andExpect(status().isOk())

                .andExpect(view().name("add"));

    }

}
