package com.example.project;

import com.example.project.dto.ProjectDto;
import com.example.project.model.Project;
import com.example.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    public ProjectDto findProjectById(int projectId){
        return projectRepository.findById(projectId).map(ProjectDto::new)
                .orElseThrow(()->new NoSuchElementException("no project"));
    }

    @Transactional
    public List<ProjectDto> getProjects(){
        return projectRepository.findAll().stream().map(ProjectDto::new).collect(Collectors.toList());
    }

    @Transactional
    public ProjectDto createProject(ProjectDto projectDto){
        Project project = projectRepository.save(new Project(projectDto));
        return new ProjectDto(project);
    }

    @Transactional
    public ProjectDto updateProject(int projectId, ProjectDto projectDto){
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NoSuchElementException("no project"));
        project.update(projectDto);
        return new ProjectDto(project);
    }

    @Transactional
    public void deleteProject(int projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NoSuchElementException("no project"));
        project.remove();
        projectRepository.delete(project);
    }
}
