package com.example;

import com.example.project.dto.ProjectDto;
import com.example.project.model.Project;
import com.example.project.repository.ProjectRepository;
import com.example.task.dto.TaskDto;
import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import com.example.user.dto.UserDto;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloApplication implements CommandLineRunner {
	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*ProjectDto projDto = new ProjectDto();
		projDto.setName("project");
		projDto.setProjectDescription("desc");
		projDto.setPriority("LOW");
		projectRepository.save(new Project(projDto));



		UserDto userDto = new UserDto();
		userDto.setName("user");
		userDto.setEmail("email");
		userDto.setRole("USER");
		userDto.setSalary(1);
		userRepository.save(new User(userDto));


		TaskDto taskDto = new TaskDto();
		taskDto.setName("task");
		taskDto.setDescription("desc");
		taskDto.setNotes("note");
		taskDto.setStartTime(null);
		taskDto.setTotalHours(0);
		taskDto.setStatus(null);
		Project project = projectRepository.findAll().get(0);
		taskRepository.save(new Task(taskDto, project));
*/
	}
}
