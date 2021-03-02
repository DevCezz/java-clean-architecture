package io.github.mat3e.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskConfiguration {

    @Bean
    TaskFacade taskFacade(
            final TaskRepository taskRepository,
            final TaskQueryRepository taskQueryRepository
    ) {
        return new TaskFacade(
                new TaskFactory(),
                taskRepository,
                taskQueryRepository);
    }
}
