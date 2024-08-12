package com.example.employee_management_system.service;

import com.example.employee_management_system.model.Project;
import com.example.employee_management_system.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository projectRepository;

    public Project findById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project save(Project project) {
        try {
            return projectRepository.save(project);
        } catch (Exception e) {
            logger.error("Error saving project: {}", e.getMessage());
            throw new RuntimeException("Failed to save project", e);
        }
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> findAll() {
        List<Project> projects = (List<Project>) projectRepository.findAll();
        return new ArrayList<>(projects);
    }

    public List<Project> findAllById(List<Long> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            return new ArrayList<>();
        }
        Iterable<Project> projectsIterable = projectRepository.findAllById(projectIds);
        List<Project> projectsList = new ArrayList<>();
        projectsIterable.forEach(projectsList::add);
        return projectsList;
    }
}
