package io.github.mat3e.task;

import io.github.mat3e.project.dto.SimpleProjectQueryEntity;
import io.github.mat3e.task.dto.TaskDto;
import org.springframework.stereotype.Service;

@Service
class TaskFactory {
    Task from(TaskDto source, SimpleProjectQueryEntity project) {
        var result = new Task(
                source.getDescription(),
                source.getDeadline(),
                project
        );
        result.setId(source.getId());
        result.setDone(source.isDone());
        result.setAdditionalComment(source.getAdditionalComment());
        return result;
    }
}
