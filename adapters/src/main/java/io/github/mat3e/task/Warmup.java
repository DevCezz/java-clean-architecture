package io.github.mat3e.task;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component("taskWarmup")
class Warmup implements ApplicationListener<ContextRefreshedEvent> {

    private final TaskFacade taskFacade;

    Warmup(final TaskFacade taskFacade) {
        this.taskFacade = taskFacade;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        if (taskQueryRepository.count() == 0) {
            var task = new Task("Example task", ZonedDateTime.now(), null);
            taskRepository.save(task);
        }
    }
}
