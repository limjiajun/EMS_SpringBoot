package com.example.employee_management_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/test")
public class TestController {

    private final DataSource dataSource;

    public TestController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/connection")
    public String testConnection() {
        try (Connection connection = dataSource.getConnection()) {
            return "Database connection successful!";
        } catch (SQLException e) {
            return "Database connection failed: " + e.getMessage();
        }
    }
}
