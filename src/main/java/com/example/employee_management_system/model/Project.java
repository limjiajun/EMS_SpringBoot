package com.example.employee_management_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @ManyToMany(mappedBy = "projects")
    @JsonBackReference  // Avoids infinite recursion
    private List<Employee> employees;

    // No-argument constructor
    public Project() {}

    // Parameterized constructor
    public Project(String name) {
        this.name = name;
    }
}
