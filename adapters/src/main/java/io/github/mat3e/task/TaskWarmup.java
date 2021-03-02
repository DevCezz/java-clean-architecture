package io.github.mat3e.task;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class TaskWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final TaskFacade taskFacade;

    Warmup(final TaskFacade taskFacade) {
        this.taskFacade = taskFacade;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        taskFacade.initializeData();
    }
}
