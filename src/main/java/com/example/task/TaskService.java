package com.example.task;
import com.example.project.model.Project;
import com.example.project.repository.ProjectRepository;
import com.example.task.dto.TaskDto;
import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TaskService {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public TaskDto findTaskById(int taskId){
        return taskRepository.findById(taskId).map(TaskDto::new)
                .orElseThrow(()->new NoSuchElementException("no task"));
    }

    @Transactional
    public List<TaskDto> findTasks(){
        return taskRepository.findAll().stream().map(TaskDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<TaskDto> findTasksByProject(int projectId){
        return taskRepository.findByProjectId(projectId).stream().map(TaskDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<TaskDto> findTasksByUser(int userId){
        return taskRepository.findByUserId(userId).stream().map(TaskDto::new).collect(Collectors.toList());
    }

    /*    @Retryable(
                value = { SQLException.class },
                backoff = @Backoff(delay = 5000))
        @Transactional(isolation = Isolation.REPEATABLE_READ)*/
    @Transactional
    public TaskDto createTask(int projectId, TaskDto taskDto){
        Project project = projectRepository.findById(projectId).orElseThrow( () -> new NoSuchElementException("no project"));
        Task task = taskRepository.save(new Task(taskDto, project));
        return new TaskDto(task);
    }

    @Transactional
    public TaskDto updateTask(int taskId, TaskDto taskDto){
        Task task = taskRepository.findById(taskId).orElseThrow( () -> new NoSuchElementException("no task"));

        task.update(taskDto);

        return new TaskDto(task);
    }

    @Transactional
    public void setTaskUser(int userId, int taskId){
        User user = userRepository.findById(userId).orElseThrow( () -> new NoSuchElementException("no user"));
        Task task = taskRepository.findById(taskId).orElseThrow( () -> new NoSuchElementException("no task"));

        task.setUser(user);
    }

    @Transactional
    public TaskDto start(int taskId, String time){
        Task task = taskRepository.findById(taskId).orElseThrow( () -> new NoSuchElementException("no task"));
        task.start(time);

        return new TaskDto(task);
    }


    @Transactional
    public void deleteTask(int taskId){
        Task task = taskRepository.findById(taskId).orElseThrow( () -> new NoSuchElementException("no task"));
        task.remove();
        taskRepository.delete(task);
    }
}
