package com.example.employee_management_system.controller;

import com.example.employee_management_system.DTO.EmployeeDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Emily");
        employeeDTO.setPosition("Financial analyst");
        employeeDTO.setDepartmentId(1L);
        employeeDTO.setProjectIds(List.of(1L));

        ResponseEntity<EmployeeDTO> response = restTemplate.postForEntity("/api/employees", employeeDTO, EmployeeDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Emily", Objects.requireNonNull(response.getBody()).getName());
        assertEquals("Financial analyst", response.getBody().getPosition());
    }
}
