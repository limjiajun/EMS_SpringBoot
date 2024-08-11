package com.example.employee_management_system.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProjectDTO {
    private Long id; // Remove @NotNull if it's not required in the body
    @NotBlank(message = "Name must not be empty")
    private String name;

    // Assuming employees are optional in the update request
    private List<EmployeeDTO> employees;
}
