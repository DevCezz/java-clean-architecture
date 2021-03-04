package io.github.mat3e.task;

import io.github.mat3e.project.dto.SimpleProject;
import io.github.mat3e.task.dto.TaskDto;

class TaskFactory {
    Task from(TaskDto source, SimpleProject project) {
        return Task.restore(new TaskSnapshot(
                source.getId(),
                source.getDescription(),
                source.isDone(),
                source.getDeadline(),
                0,
                source.getAdditionalComment(),
                project.getSnapshot()
        ));
    }
}
