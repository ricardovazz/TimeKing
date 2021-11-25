package com.example.project.model;
import com.example.project.dto.ProjectDto;
import com.example.task.model.Task;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(
            name = "name", // Name of the table column - default: fieldname
            nullable = false, // Is the column nullable - default: true
            unique = false, // Is the value of this column unique across the table - default: false
            insertable = true, // Should this field be included in an INSERT command - default: true
            updatable = true, // Should this field be included in an UPDATE command - default: true
            length = 45 // Maximum number of chars (only relevant to string fields) - default: 255
    )
    private String name;


    @Column(
            name = "project_description",
            nullable = false, // Is the column nullable - default: true
            length = 100 // Maximum number of chars (only relevant to string fields) - default: 255
    )
    private String projectDescription;


    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;


    // @OrderBy("firstName asc")

    // @OneToOne's fetch type is EAGER by default
    // Lists + Sets fetch type is LAZY by default

    // Mark children elements as "CascadeType.ALL" to refresh/delete/... them if the parent refreshes/deletes/...
    // CascadeType.ALL contains PERSIST, REMOVE, REFRESH, MERGE, DETACH
    // Remove Child Records, when the child record is set to null in the parents collection of children
    // by setting "orphanRemoval = true"
    // Add mappedBy to the non-owning side of the relationship to get a bi-directional navigation
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch=FetchType.EAGER, orphanRemoval=true)
    private Set<Task> tasks = new HashSet<>();


    public Project(){
    }

    public Project(ProjectDto projectDto){
        if (projectDto.getPriority() != null)
            setPriority(projectDto.getPriority());
        else
            setPriority(Priority.LOW.toString());

        this.name = projectDto.getName();
        this.projectDescription = projectDto.getProjectDescription();
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        if (priority == null)
            this.priority = Priority.LOW;

        try {
            this.priority = Priority.valueOf(priority);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Priority does not exist");
        }
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void update(ProjectDto projectDto){
        this.projectDescription = projectDto.getProjectDescription();
        this.name = projectDto.getName();
        setPriority(projectDto.getPriority());
    }

    public void remove(){
        this.getTasks().forEach(task -> task.remove());
        this.tasks = null;
    }
}