package com.example.task.controller;

import com.example.task.TaskService;
import com.example.task.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskController {


    @Autowired
    private TaskService taskService;


    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Integer id){
        try {
            return new ResponseEntity<TaskDto>(taskService.findTaskById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TaskDto>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto, @PathVariable Integer id){
        try {
            taskService.updateTask(id, taskDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<TaskDto>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/all")
    public List<TaskDto> getAllProject() {
        return taskService.findTasks();
    }

    @PostMapping("/add/{projectId}")
    public String add(@RequestBody TaskDto taskDto, @PathVariable Integer projectId){
        taskService.createTask(projectId, taskDto);
        return "New task is added";
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable Integer id){
        taskService.deleteTask(id);
        return "Task " + id.toString() + "was deleted";
    }
}
