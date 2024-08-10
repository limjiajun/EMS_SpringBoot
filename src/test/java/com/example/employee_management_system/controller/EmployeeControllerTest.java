package com.example.employee_management_system.controller;

import com.example.employee_management_system.DTO.EmployeeDTO;
import com.example.employee_management_system.controller.EmployeeController;
import com.example.employee_management_system.model.Department;
import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.model.Project;
import com.example.employee_management_system.service.DepartmentService;
import com.example.employee_management_system.service.EmployeeService;
import com.example.employee_management_system.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testCreateEmployee_Success() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Abu");
        employeeDTO.setPosition("Financial analyst");
        employeeDTO.setDepartmentId(1L);
        employeeDTO.setProjectIds(List.of(1L));

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Abu123");
        employee.setPosition("Financial analyst");
        employee.setDepartment(new Department());
        employee.setProjects(List.of(new Project()));

        when(employeeService.save(any(Employee.class))).thenReturn(employee);
        when(departmentService.findById(anyLong())).thenReturn(new Department());
        when(projectService.findAllById(anyList())).thenReturn(List.of(new Project()));

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Abu123"))
                .andExpect(jsonPath("$.position").value("Financial analyst"));
    }
}
