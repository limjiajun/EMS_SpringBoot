package com.example.employee_management_system.service;

import com.example.employee_management_system.model.Project;
import com.example.employee_management_system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project findById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> findAll() {
        // Fetch data from the database
        List<Project> projects = (List<Project>) projectRepository.findAll();

        // Return as List
        return new ArrayList<>(projects);
    }

    public List<Project> findAllById(List<Long> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            return new ArrayList<>();
        }
        // Fetch projects by their IDs
        Iterable<Project> projectsIterable = projectRepository.findAllById(projectIds);

        // Convert Iterable to List
        List<Project> projectsList = new ArrayList<>();
        projectsIterable.forEach(projectsList::add);

        return projectsList;
    }
}
