package com.example.task.dto;

import com.example.task.model.Task;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

public class TaskDto implements Serializable {
    private int id;
    private String name;
    private String startTime;
    private Integer totalHours;
    private String status;
    private String notes;
    private String description;

    public TaskDto() {
    }

    public TaskDto(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.startTime = task.getStartTime() == null ? null : task.getStartTime().toString();
        this.totalHours = task.getTotalHours();
        this.status = task.getStatus().toString();
        this.notes = task.getNote();
        this.description = task.getDescription();
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getTotalHours() {
        return this.totalHours;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", totalHours=" + totalHours +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
