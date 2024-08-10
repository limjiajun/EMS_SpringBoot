package com.example.employee_management_system.controller;
import com.example.employee_management_system.DTO.EmployeeDTO;

import com.example.employee_management_system.ErrorResponse;
import com.example.employee_management_system.exception.ResourceNotFoundException;
import com.example.employee_management_system.model.Department;
import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.model.Project;
import com.example.employee_management_system.service.DepartmentService;
import com.example.employee_management_system.service.EmployeeService;
import com.example.employee_management_system.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "APIs for managing employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class)))
    })
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @Operation(summary = "Get employee by ID", description = "Retrieve an employee by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved employee",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public Employee getEmployeeById(
            @Parameter(description = "ID of employee to be retrieved", required = true)
            @PathVariable Long id) {
        return employeeService.findById(id);
    }

    @Operation(summary = "Create a new employee", description = "Add a new employee to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class)))
    })
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(new ErrorResponse(errors));
        }

        try {
            // Find department by ID
            Department department = departmentService.findById(employeeDTO.getDepartmentId());
            if (department == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Department not found"));
            }

            // Find projects by IDs
            List<Project> projects = projectService.findAllById(employeeDTO.getProjectIds());
            if (projects.isEmpty() && !employeeDTO.getProjectIds().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("One or more projects not found"));
            }

            // Create and save new employee
            Employee employee = new Employee();
            employee.setName(employeeDTO.getName());
            employee.setPosition(employeeDTO.getPosition());
            employee.setDepartment(department);
            employee.setProjects(projects);

            Employee newEmployee = employeeService.save(employee);

            // Convert to DTO and return
            EmployeeDTO newEmployeeDTO = new EmployeeDTO();
            newEmployeeDTO.setId(newEmployee.getId());
            newEmployeeDTO.setName(newEmployee.getName());
            newEmployeeDTO.setPosition(newEmployee.getPosition());
            newEmployeeDTO.setDepartmentId(newEmployee.getDepartment().getId());
            newEmployeeDTO.setProjectIds(newEmployee.getProjects().stream().map(Project::getId).toList());

            return ResponseEntity.status(HttpStatus.CREATED).body(newEmployeeDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid argument: " + e.getMessage()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Resource not found: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred"));
        }
    }




    @Operation(summary = "Update an employee", description = "Update an existing employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PutMapping("/{id}")

    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {
        Employee existingEmployee = employeeService.findById(id);
        if (existingEmployee == null) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }

        // Update the existing employee with the new data
        existingEmployee.setName(employee.getName());
        existingEmployee.setPosition(employee.getPosition());

        // Update the department if it exists
        if (employee.getDepartment() != null) {
            Department department = departmentService.findById(employee.getDepartment().getId());
            if (department != null) {
                existingEmployee.setDepartment(department);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }

        // Update the projects if they exist
        if (employee.getProjects() != null && !employee.getProjects().isEmpty()) {
            List<Project> projects = projectService.findAllById(employee.getProjects().stream()
                    .map(Project::getId)
                    .toList());
            existingEmployee.setProjects(projects);
        }

        Employee updatedEmployee = employeeService.save(existingEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }




    @Operation(summary = "Delete an employee", description = "Delete an employee by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("/{id}")
    public void deleteEmployee(
            @Parameter(description = "ID of employee to be deleted", required = true)
            @PathVariable Long id) {
        employeeService.deleteById(id);
    }

    @Operation(summary = "Assign projects to an employee", description = "Assign one or more projects to an employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects assigned to employee",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee or projects not found")
    })
    @PostMapping("/{employeeId}/projects")
    public ResponseEntity<Employee> assignProjectsToEmployee(
            @Parameter(description = "ID of employee to be assigned projects", required = true)
            @PathVariable Long employeeId,
            @Parameter(description = "List of project IDs to be assigned", required = true)
            @RequestBody List<Long> projectIds) {

        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Project> projects = projectService.findAllById(projectIds);
        if (projects == null || projects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        employee.setProjects(projects);
        Employee updatedEmployee = employeeService.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

}

