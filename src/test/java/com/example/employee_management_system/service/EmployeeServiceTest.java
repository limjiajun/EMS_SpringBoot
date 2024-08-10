package com.example.employee_management_system.service;

import com.example.employee_management_system.exception.ResourceNotFoundException;
import com.example.employee_management_system.model.Department;
import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.model.Project;
import com.example.employee_management_system.repository.DepartmentRepository;
import com.example.employee_management_system.repository.EmployeeRepository;
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

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository; // Mocking the EmployeeRepository dependency

    @Mock
    private DepartmentRepository departmentRepository; // Mocking the DepartmentRepository dependency

    @Mock
    private ProjectRepository projectRepository; // Mocking the ProjectRepository dependency

    @InjectMocks
    private EmployeeService employeeService; // Injecting mocks into EmployeeService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks and injects them into the service
    }

    @Test
    void testFindAll() {
        // Creating a list of mock Employee objects
        List<Employee> mockEmployees = Arrays.asList(
                new Employee("Alice", "HR Manager", new Department("HR")),
                new Employee("Jason", "IT Specialist", new Department("IT")),
                new Employee("Carren", "Marketing Executive", new Department("Marketing"))
        );

        // Configuring the mock repository to return the mock list when findAll() is called
        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        // Calling the findAll method
        List<Employee> result = employeeService.findAll();

        // Verify the result size and content
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Alice", result.get(0).getName());

        // Verifying that findAll() was called exactly once on the repository
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        // Arrange
        Department department = new Department("HR");
        Employee employee = new Employee("Alice", "HR Manager", department);
        employee.setId(1L);

        // Configuring the mock repository to return the mock employee when findById(1L) is called
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Calling the findById method
        Employee result = employeeService.findById(1L);

        // Verify the result is not null and has the expected name
        assertNotNull(result);
        assertEquals("Alice", result.getName());

        // Verifying that findById(1L) was called exactly once on the repository
        verify(employeeRepository, times(1)).findById(1L);
    }

//    @Test
//    void testFindByIdThrowsResourceNotFoundException() {
//        // Configuring the mock repository to return an empty Optional when findById(1L) is called
//        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // Verifying a ResourceNotFoundException is thrown
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            employeeService.findById(1L);
//        });
//
//        assertEquals("Employee not found", exception.getMessage());
//
//        // Verifying that findById(1L) was called exactly once on the repository
//        verify(employeeRepository, times(1)).findById(1L);
//    }

    @Test
    void testSave() {
        // Creating a mock Employee object
        Department department = new Department("HR");
        Employee employee = new Employee("Alice", "HR Manager", department);

        // Configuring the mock repository to return the mock employee when save() is called
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Calling the save method
        Employee result = employeeService.save(employee);

        // Verify the result is not null and has the expected name
        assertNotNull(result);
        assertEquals("Alice", result.getName());

        // Verifying that save(employee) was called exactly once on the repository
        verify(employeeRepository, times(1)).save(employee);
    }

//    @Test
//    void testDeleteById() {
//        // Calling the deleteById method
//        employeeService.deleteById(1L);
//
//        // Verifying that deleteById(1L) was called exactly once on the repository
//        verify(employeeRepository, times(1)).deleteById(1L);
//    }
}
