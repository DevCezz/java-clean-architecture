package io.github.mat3e.task;

import io.github.mat3e.project.dto.SimpleProjectQueryEntity;
import io.github.mat3e.task.dto.TaskDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class TaskFacade {
    private final TaskRepository taskRepository;
    private final TaskFactory taskFactory;

    TaskFacade(final TaskFactory taskFactory, final TaskRepository taskRepository) {
        this.taskFactory = taskFactory;
        this.taskRepository = taskRepository;
    }

    public List<TaskDto> saveAll(final List<TaskDto> tasks, SimpleProjectQueryEntity project) {
        return taskRepository.saveAll(tasks.stream().map(dto -> taskFactory.from(dto, project)).collect(toList())).stream()
                .map(Task::toDto)
                .collect(Collectors.toList());
    }

    TaskDto save(TaskDto toSave) {
        Task save = taskRepository.save(
                taskRepository.findById(toSave.getId())
                        .map(existingTask -> {
                            if (existingTask.isDone() != toSave.isDone()) {
                                existingTask.setChangesCount(existingTask.getChangesCount() + 1);
                                existingTask.setDone(toSave.isDone());
                            }
                            existingTask.setAdditionalComment(toSave.getAdditionalComment());
                            existingTask.setDeadline(toSave.getDeadline());
                            existingTask.setDescription(toSave.getDescription());
                            return existingTask;
                        }).orElseGet(() -> {
                    var result = new Task(toSave.getDescription(), toSave.getDeadline(), null);
                    result.setAdditionalComment(toSave.getAdditionalComment());
                    return result;
                })
        );
        return toDto(save);
    }

    void delete(int id) {
        taskRepository.deleteById(id);
    }

    private TaskDto toDto(Task task) {
        return TaskDto.builder()
                .withId(task.getId())
                .withDescription(task.getDescription())
                .withDone(task.isDone())
                .withDeadline(task.getDeadline())
                .withAdditionalComment(task.getAdditionalComment())
                .build();
    }
}
