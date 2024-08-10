package com.example.employee_management_system.service;

import com.example.employee_management_system.exception.ResourceNotFoundException;
import com.example.employee_management_system.model.Department;
import com.example.employee_management_system.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public ArrayList<Department> findAll() {
        // Fetch data from the database
        List<Department> departments = departmentRepository.findAll();

        // Convert List to ArrayList if necessary
        return new ArrayList<>(departments);
    }

    public Department findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return departmentRepository.findById(id).orElse(null);
    }
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return departmentRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    }

}
