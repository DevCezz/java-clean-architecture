package io.github.mat3e.task;

class TaskInitializer {

    private final TaskRepository taskRepository;
    private final TaskQueryRepository taskQueryRepository;

    TaskInitializer(final TaskRepository taskRepository, final TaskQueryRepository taskQueryRepository) {
        this.taskRepository = taskRepository;
        this.taskQueryRepository = taskQueryRepository;
    }
}
