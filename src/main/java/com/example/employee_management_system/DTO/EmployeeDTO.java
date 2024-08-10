package com.example.employee_management_system.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EmployeeDTO {
    private Long id;
    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Position must not be empty")
    private String position;

    @NotNull(message = "Department ID must not be null")
    private Long departmentId;

    @NotNull(message = "Project IDs must not be null")
    private List<@NotNull(message = "Project ID must not be null") Long> projectIds;


}

