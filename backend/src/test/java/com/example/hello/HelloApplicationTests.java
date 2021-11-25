package com.example.hello;

import com.example.project.ProjectService;
import com.example.project.dto.ProjectDto;
import com.example.project.model.Project;
import com.example.project.repository.ProjectRepository;
import com.example.task.TaskService;
import com.example.task.dto.TaskDto;
import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import com.example.user.dto.UserDto;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

@SpringBootTest
class HelloApplicationTests {


	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskService taskService;

	@Autowired
	ProjectService projectService;

	@Test
	void contextLoads() {
	}

	@Test
	public void cleanup(){
		userRepository.deleteAll();
		projectRepository.deleteAll();
		taskRepository.deleteAll();
	}

	@Test
	public void example() {


		System.out.println("****************Project******************");
		projectRepository.findAll().forEach(System.out::println);

		//
		System.out.println("****************User******************");
		userRepository.findAll().forEach(x -> System.out.println(x.getId()));

		//
		System.out.println("****************Tasks******************");
		taskRepository.findAll().forEach(x -> System.out.println(x.getId()));
		Task task = taskRepository.findAll().get(0);

		User u = userRepository.findAll().get(0);

		task.setUser(u);

		System.out.println(task.getUser());

		System.out.println(u.getTasks());

		Timestamp timestamp = Timestamp.valueOf("2021-11-25 13:00:00.0");

		taskService.start(task.getId(), timestamp.toString());

		System.out.println("****************Tasks******************");
		taskRepository.findAll().forEach(x -> System.out.println(x.getStartTime()));


		ProjectDto projDto = new ProjectDto();
		projDto.setName("project2");
		projDto.setProjectDescription("desc2");
		projDto.setPriority("MEDIUM");

		Project project = projectRepository.findAll().get(0);
		projectService.updateProject(project.getId(), projDto);

		System.out.println("****************Project******************");
		projectService.getProjects().forEach(x -> System.out.println(x.getName()+x.getProjectDescription()));


		userRepository.deleteAll();
		projectRepository.deleteAll();
		taskRepository.deleteAll();
	}


}
