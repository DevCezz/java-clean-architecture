package io.github.mat3e;

import io.github.mat3e.project.ProjectRepository;
import io.github.mat3e.task.Task;
import io.github.mat3e.task.TaskRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.time.ZonedDateTime;

@SpringBootApplication
public class JavaCleanArchitectureApplication implements ApplicationListener<ContextRefreshedEvent> {
    public static void main(String[] args) {
        SpringApplication.run(JavaCleanArchitectureApplication.class, args);
    }

    private final TaskRepository taskRepository;

    public JavaCleanArchitectureApplication(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (taskRepository.count() == 0) {
            var task = new Task("Example task", ZonedDateTime.now(), null);
            taskRepository.save(task);
        }
    }
}
