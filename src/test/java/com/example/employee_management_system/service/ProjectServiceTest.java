package com.example.employee_management_system.service;

import com.example.employee_management_system.exception.ResourceNotFoundException;
import com.example.employee_management_system.model.Project;
import com.example.employee_management_system.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository; // Mocking the ProjectRepository dependency

    @InjectMocks
    private ProjectService projectService; // Injecting the mock into ProjectService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks and injects them into the service
    }

    @Test
    void testFindAll() {
        // Creating a list of mock Project objects
        List<Project> mockProjects = Arrays.asList(
                new Project("Project A"),
                new Project("Project B"),
                new Project("Project C"),
                new Project("string1")
        );

        // Configuring the mock repository to return the mock list when findAll() is called
        when(projectRepository.findAll()).thenReturn(mockProjects);

        // Calling the findAll method
        List<Project> result = projectService.findAll();

        // Verify the result size and content
        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals("Project A", result.get(0).getName());

        // Verifying that findAll() was called exactly once on the repository
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        // Arrange
        Project project = new Project("Project A");
        project.setId(1L);

        // Configuring the mock repository to return the mock project when findById(1L) is called
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        // Calling the findById method
        Project result = projectService.findById(1L);

        // Verify the result is not null and has the expected name
        assertNotNull(result);
        assertEquals("Project A", result.getName());

        // Verifying that findById(1L) was called exactly once on the repository
        verify(projectRepository, times(1)).findById(1L);
    }

//    @Test
//    void testFindByIdThrowsResourceNotFoundException() {
//        // Configuring the mock repository to return an empty Optional when findById(1L) is called
//        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // Verifying a ResourceNotFoundException is thrown
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            projectService.findById(1L);
//        });
//
//        assertEquals("Project not found", exception.getMessage());
//
//        // Verifying that findById(1L) was called exactly once on the repository
//        verify(projectRepository, times(1)).findById(1L);
//    }

    @Test
    void testSave() {
        // Creating a mock project object
        Project project = new Project("Project A");

        // Configuring the mock repository to return the mock project when save() is called
        when(projectRepository.save(project)).thenReturn(project);

        // Calling the save method
        Project result = projectService.save(project);

        // Verify the result is not null and has the expected name
        assertNotNull(result);
        assertEquals("Project A", result.getName());

        // Verifying that save(project) was called exactly once on the repository
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testDeleteById() {
        // Calling the deleteById method
        projectService.deleteById(1L);

        // Verifying that deleteById(1L) was called exactly once on the repository
        verify(projectRepository, times(1)).deleteById(1L);
    }
}
