package io.github.mat3e.task;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.time.ZonedDateTime;

class Warmup implements ApplicationListener<ContextRefreshedEvent> {

    private final TaskRepository taskRepository;

    Warmup(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        if (taskRepository.count() == 0) {
            var task = new Task("Example task", ZonedDateTime.now(), null);
            taskRepository.save(task);
        }
    }
}
