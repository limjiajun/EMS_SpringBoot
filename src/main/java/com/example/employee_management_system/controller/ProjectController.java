package com.example.employee_management_system.controller;


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
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
                    content = @Content(schema = @Schema(implementation = Project.class)))
            @RequestBody @Valid Project project) {
        logger.info("Creating project: {}", project.getName());
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
    public Project updateProject(
            @Parameter(description = "ID of the project to be updated", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated project object", required = true,
                    content = @Content(schema = @Schema(implementation = Project.class)))
            @RequestBody @Valid Project project) {
        project.setId(id);
        logger.info("Updating project with ID: {}", id);
        return projectService.save(project);
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
