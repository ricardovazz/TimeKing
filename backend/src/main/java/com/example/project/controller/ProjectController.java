package com.example.project.controller;

import com.example.project.ProjectService;
import com.example.project.dto.ProjectDto;
import com.example.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectController {


    @Autowired
    private ProjectService projectService;


    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable Integer id){
        try {
            return new ResponseEntity<ProjectDto>(projectService.findProjectById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<ProjectDto>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto projectDto, @PathVariable Integer id){
        try {
            projectService.updateProject(id, projectDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<ProjectDto>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/all")
    public List<ProjectDto> getAllProject() {
        return projectService.getProjects();
    }

    @PostMapping("/add")
    public String add(@RequestBody ProjectDto projectDto){
        projectService.createProject(projectDto);
        return "New project is added";
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable Integer id){
        projectService.deleteProject(id);
        return "Project " + id.toString() + "was deleted";
    }
}
