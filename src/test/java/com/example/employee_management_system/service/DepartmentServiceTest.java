package com.example.employee_management_system.service;

import com.example.employee_management_system.exception.ResourceNotFoundException;
import com.example.employee_management_system.model.Department;
import com.example.employee_management_system.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository; // Mocking the DepartmentRepository dependency

    @InjectMocks
    private DepartmentService departmentService; // Injecting the mock into DepartmentService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks and injects them into the service
    }

    @Test
    void testFindAll() {

        // Creating a list of mock Department objects
        List<Department> mockDepartments = new ArrayList<>();
        mockDepartments.add(new Department("HR"));
        mockDepartments.add(new Department("Finance"));

        // Configuring the mock repository to return the mock list when findAll() is called
        when(departmentRepository.findAll()).thenReturn(mockDepartments);

        // Calling the findAll method
        ArrayList<Department> result = departmentService.findAll();

        // Verify the result size is 2
        assertEquals(2, result.size());

        // Verify findAll()
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        // Arrange
        // Creating a mock Department object
        Department department = new Department("IT");

        // Configuring the mock repository to return the mock department when findById(1L) is called
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));


        // Calling the findById method
        Department result = departmentService.findById(1L);


        // Verify  the result is not null
        assertNotNull(result);

        // Verify  the department name is "IT"
        assertEquals("IT", result.getName());

        // Verifying that findById(1L)
        verify(departmentRepository, times(1)).findById(1L);
    }


    // @Test
    // void testFindByIdThrowsResourceNotFoundException() {
    //
    //     // Configuring the mock repository to return an empty Optional when findById(1L) is called
    //     when(departmentRepository.findById(1L)).thenReturn(Optional.empty());
    //
    //
    //     // Verify a ResourceNotFoundException
    //     Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
    //         departmentService.findById(1L);
    //     });
    //
    //     assertEquals("Department not found", exception.getMessage());
    //
    //     // Verifying that findById(1L) was called exactly once on the repository
    //     verify(departmentRepository, times(1)).findById(1L);
    // }

    @Test
    void testSave() {

        // Create a mock object
        Department department = new Department("Marketing");

        // Configuring the mock repository to return the mock department when save() is called
        when(departmentRepository.save(department)).thenReturn(department);


        // Calling the save method
        Department result = departmentService.save(department);


        // Verify the result is not null
        assertNotNull(result);

        // Verify department name is "Marketing"
        assertEquals("Marketing", result.getName());

        // Verify save(department)
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void testDeleteById() {

        // Calling the deleteById method
        departmentService.deleteById(1L);


        // Verify deleteById(1L)
        verify(departmentRepository, times(1)).deleteById(1L);
    }
}
