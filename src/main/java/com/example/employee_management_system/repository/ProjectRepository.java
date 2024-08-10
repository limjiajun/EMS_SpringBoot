package com.example.employee_management_system.repository;

import com.example.employee_management_system.model.Project;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    @Override
    List<Project> findAllById(Iterable<Long> ids);
}