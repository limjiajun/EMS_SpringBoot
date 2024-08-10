package com.example.employee_management_system.service;

import com.example.employee_management_system.DTO.EmployeeDTO;
import com.example.employee_management_system.exception.ResourceNotFoundException;
import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Transactional
    public Employee save(Employee employee) {
        // Add any validation logic here
        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteById(Long id) {
        // Check if the employee exists before deleting
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    public Employee createEmployee(EmployeeDTO employeeDTO) {
        // Convert EmployeeDTO to Employee entity
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setPosition(employeeDTO.getPosition());

        // Set department and projects if needed
        // departmentService.findById(employeeDTO.getDepartmentId());
        // employee.setDepartment(department);

        // Save the employee entity
        return employeeRepository.save(employee);
    }

}

