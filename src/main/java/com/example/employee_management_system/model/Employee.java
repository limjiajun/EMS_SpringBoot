package com.example.employee_management_system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Position is mandatory")
    private String position;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    @NotNull(message = "Department is mandatory")
    @JsonManagedReference  // Manages serialization of department object
    private Department department;

    @ManyToMany
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @JsonManagedReference  // Manages serialization of projects list
    private List<Project> projects;

    // No-argument constructor
    public Employee() {
    }

    // Parameterized constructor
    public Employee(String name, String position, Department department) {
        this.name = name;
        this.position = position;
        this.department = department;
    }

    // Parameterized constructor with projects
    public Employee(String name, String position, Department department, List<Project> projects) {
        this.name = name;
        this.position = position;
        this.department = department;
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", department=" + department +
                ", projects=" + projects +
                '}';
    }
}
