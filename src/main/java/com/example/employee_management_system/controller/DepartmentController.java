package com.example.employee_management_system.controller;

import com.example.employee_management_system.DTO.DepartmentDTO;
import com.example.employee_management_system.model.Department;
import com.example.employee_management_system.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "Department", description = "Department management APIs")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Operation(summary = "Get all departments", description = "Retrieve a list of all departments.")
    @ApiResponse(responseCode = "200", description = "Found all departments",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Department.class)))
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.findAll();
    }

    @Operation(summary = "Get department by ID", description = "Retrieve a department by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Department.class))),
            @ApiResponse(responseCode = "404", description = "Department not found", content = @Content)
    })
    @GetMapping("/{id}")
    public Department getDepartmentById(
            @Parameter(description = "ID of the department to be retrieved", required = true)
            @PathVariable Long id) {
        return departmentService.findById(id);
    }

    @Operation(summary = "Create a new department", description = "Add a new department to the system.")
    @ApiResponse(responseCode = "201", description = "Department created",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Department.class)))
    @PostMapping
    public Department createDepartment(
            @Parameter(description = "Department object to be created", required = true,
                    content = @Content(schema = @Schema(implementation = DepartmentDTO.class)))
            @RequestBody DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        return departmentService.save(department);
    }

    @Operation(summary = "Update a department", description = "Update an existing department.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Department.class))),
            @ApiResponse(responseCode = "404", description = "Department not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @Parameter(description = "ID of the department to be updated", required = true)
            @PathVariable Long id,
            @RequestBody DepartmentDTO departmentDTO) {

        Department existingDepartment = departmentService.findById(id);
        if (existingDepartment == null) {
            return ResponseEntity.notFound().build();
        }

        existingDepartment.setName(departmentDTO.getName()); // Update only the name
        Department updatedDepartment = departmentService.save(existingDepartment);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedDepartment);
    }


    @Operation(summary = "Delete a department", description = "Delete a department by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Department not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void deleteDepartment(
            @Parameter(description = "ID of the department to be deleted", required = true)
            @PathVariable Long id) {
        departmentService.deleteById(id);
    }
}

