package com.example.project.dto;

import com.example.project.model.Project;

import java.io.Serializable;

public class ProjectDto implements Serializable {
    private int project_id;
    private String name;
    private String projectDescription;
    private String priority;

    public ProjectDto(){
    }

    public ProjectDto(Project project) {
        this.project_id = project.getId();
        this.name = project.getName();
        this.projectDescription = project.getProjectDescription();
        this.priority = project.getPriority().toString();
    }

    public int getProject_id() {
        return project_id;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                "project_id=" + project_id +
                ", name='" + name + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
