package com.example.user.model;

import com.example.project.model.Project;
import com.example.task.model.Task;
import com.example.user.dto.UserDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    public enum Role {
        USER, ADMIN
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

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(
            name = "email", // Name of the table column - default: fieldname
            nullable = false, // Is the column nullable - default: true
            length = 45 // Maximum number of chars (only relevant to string fields) - default: 255
    )
    private String email;

    @Column(
            name = "salary"
    )
    private Integer salary;


    // @OrderBy("firstName asc")

    // @OneToOne's fetch type is EAGER by default
    // Lists + Sets fetch type is LAZY by default

    // Mark children elements as "CascadeType.ALL" to refresh/delete/... them if the parent refreshes/deletes/...
    // CascadeType.ALL contains PERSIST, REMOVE, REFRESH, MERGE, DETACH
    // Remove Child Records, when the child record is set to null in the parents collection of children
    // by setting "orphanRemoval = true"
    // Add mappedBy to the non-owning side of the relationship to get a bi-directional navigation

    //  eager: loads every task when user loads
    //  cascade all: any state changes made in task apply to user as well
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch=FetchType.EAGER)
    private final Set<Task> tasks = new HashSet<>();


    /*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "enrollment",
        joinColumns = {@JoinColumn(name = "user_id",
                referencedColumnName = "id"
        )},
            inverseJoinColumns = {@JoinColumn(name = "task_id",
                    referencedColumnName = "id"
            )}
    )*/


    public User() {
    }

    public User(UserDto userDto) {
        if (userDto.getRole() != null)
            setRole(userDto.getRole());
        else
            setRole(Role.USER.toString());

        this.name = userDto.getName();
        this.email = userDto.getEmail();
        this.salary = userDto.getSalary();
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

    public Role getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role == null)
            this.role = Role.USER;

        try {
            this.role = Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            this.role = Role.USER;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}