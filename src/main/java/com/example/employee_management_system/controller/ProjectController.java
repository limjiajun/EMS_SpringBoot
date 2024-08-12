package com.example.employee_management_system.controller;


import com.example.employee_management_system.DTO.EmployeeDTO;
import com.example.employee_management_system.DTO.ProjectDTO;
import com.example.employee_management_system.model.Project;
import com.example.employee_management_system.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/projects")
@SecurityRequirement(name = "BasicAuth")
@Tag(name = "Project", description = "Project management APIs")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "Get all projects", description = "Retrieve a list of all projects.")
    @ApiResponse(responseCode = "200", description = "Found all projects",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Project.class)))
    @GetMapping
    public List<Project> getAllProjects() {
        logger.info("Fetching all projects");
        return projectService.findAll();
    }

    @Operation(summary = "Get project by ID", description = "Retrieve a project by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @GetMapping("/{id}")
    public Project getProjectById(
            @Parameter(description = "ID of the project to be retrieved", required = true)
            @PathVariable Long id) {
        logger.info("Fetching project with ID: {}", id);
        return projectService.findById(id);
    }

    @Operation(summary = "Create a new project", description = "Add a new project to the system.")
    @ApiResponse(responseCode = "201", description = "Project created",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Project.class)))
    @PostMapping
    public Project createProject(
            @Parameter(description = "Project object to be created", required = true,
                    content = @Content(schema = @Schema(implementation = ProjectDTO.class)))
            @RequestBody @Valid ProjectDTO projectDTO) {
        logger.info("Creating project: {}", projectDTO.getName());

        // Convert ProjectDTO to Project entity
        Project project = new Project();
        project.setName(projectDTO.getName());

        return projectService.save(project);
    }


    @Operation(summary = "Update a project", description = "Update an existing project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable Long id,
            @RequestBody @Valid ProjectDTO projectDTO) {

        // Find existing project
        Project existingProject = projectService.findById(id);
        if (existingProject == null) {
            return ResponseEntity.notFound().build();
        }

        // Update only the name from the request body
        existingProject.setName(projectDTO.getName());
        Project updatedProject = projectService.save(existingProject);

        // Convert updatedProject to ProjectDTO before returning
        ProjectDTO responseDTO = new ProjectDTO();
        responseDTO.setId(updatedProject.getId());
        responseDTO.setName(updatedProject.getName());
        responseDTO.setEmployees(updatedProject.getEmployees().stream()
                .map(employee -> {
                    EmployeeDTO employeeDTO = new EmployeeDTO();
                    employeeDTO.setId(employee.getId());
                    employeeDTO.setName(employee.getName());
                    employeeDTO.setPosition(employee.getPosition());
                    employeeDTO.setDepartmentId(employee.getDepartment().getId());
                    employeeDTO.setProjectIds(employee.getProjects().stream()
                            .map(Project::getId)
                            .collect(Collectors.toList()));
                    return employeeDTO;
                })
                .collect(Collectors.toList()));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDTO);
    }



    @Operation(summary = "Delete a project", description = "Delete a project by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void deleteProject(
            @Parameter(description = "ID of the project to be deleted", required = true)
            @PathVariable Long id) {
        logger.info("Deleting project with ID: {}", id);
        projectService.deleteById(id);
    }
}
