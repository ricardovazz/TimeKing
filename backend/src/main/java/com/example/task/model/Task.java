package com.example.task.model;

import com.example.project.model.Project;
import com.example.task.dto.TaskDto;
import com.example.user.model.User;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "task")
public class Task {

    public enum Status {
        AVAILABLE, PAUSED, INPROGRESS, COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(
            name = "name", // Name of the table column - default: fieldname
            nullable = false, // Is the column nullable - default: true
            length = 45 // Maximum number of chars (only relevant to string fields) - default: 255
    )
    private String name;

    //@Temporal(TemporalType.TIMESTAMP) if it was java.util.Date
    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "total_hours")
    private Integer totalHours;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    private String note;

    @Column(
            name = "description", // Name of the table column - default: fieldname
            nullable = false // Is the column nullable - default: true
    )
    private String description;

    //However, I think the task does not need to know that a project exists
    // Task <-> Project
    //  *        1
    @ManyToOne//Many tasks to one project
    @JoinColumn(name = "project_id")
    private Project project;


    // Task <-> User
    //  *        0,1
    @ManyToOne//Many tasks to one project
    @JoinColumn(name = "user_id")
    private User user;


    public Task() {
    }

    public Task(TaskDto taskDto, Project project) {

        setStatus("AVAILABLE");
        this.user = null;
        this.startTime = null;
        this.totalHours = 0;

        setProject(project);
        this.name = taskDto.getName();
        this.note = taskDto.getNotes();
        this.description = taskDto.getDescription();
    }

    @Transient
    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Timestamp parseTimestamp(String timestamp) {
        try {
            return new Timestamp(DATE_TIME_FORMAT.parse(timestamp).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = parseTimestamp(startTime);
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {

        try {
            this.status = Task.Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status does not exist");
        }
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        project.addTask(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.addTask(this);
    }

    public void start(String time){
        setStartTime(time);
        this.status = Status.INPROGRESS;
    }

    public void pause(String pauseTime){
        if (this.status == Status.INPROGRESS) {
            this.status = Status.PAUSED;
            Timestamp finalTime = parseTimestamp(pauseTime);
            long diff = finalTime.getTime() - getStartTime().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            int aux = getTotalHours();
            setTotalHours((int)(minutes) + aux);
            setStartTime(null);
        }
    }

    public void resume(String startTime){
        if (this.status == Status.PAUSED) {
            this.status = Status.INPROGRESS;
            setStartTime(startTime);
        }
    }

    public void complete(String finishTime){
        if (this.status == Status.INPROGRESS) {
            this.status = Status.COMPLETED;
            Timestamp finalTime = parseTimestamp(finishTime);
            long diff = finalTime.getTime() - getStartTime().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            int aux = getTotalHours();
            setTotalHours((int)(minutes) + aux);
            setStartTime(null);
        }
    }

    public void update(TaskDto taskDto) {
        setName(taskDto.getName());
        setDescription(taskDto.getDescription());
        setStatus(taskDto.getStatus());
        setNote(taskDto.getNotes());
    }

    public void remove(){
        this.project.getTasks().remove(this);
        this.project = null;
        this.user.getTasks().remove(this);
        this.user = null;
    }
}
