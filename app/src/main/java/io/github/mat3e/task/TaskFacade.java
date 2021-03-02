package io.github.mat3e.task;

import io.github.mat3e.project.dto.SimpleProjectQueryEntity;
import io.github.mat3e.task.dto.TaskDto;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TaskFacade {
    private final TaskRepository taskRepository;
    private final TaskQueryRepository taskQueryRepository;
    private final TaskFactory taskFactory;

    TaskFacade(final TaskFactory taskFactory, final TaskRepository taskRepository, final TaskQueryRepository taskQueryRepository) {
        this.taskFactory = taskFactory;
        this.taskRepository = taskRepository;
        this.taskQueryRepository = taskQueryRepository;
    }

    public List<TaskDto> saveAll(final List<TaskDto> tasks, SimpleProjectQueryEntity project) {
        return taskRepository.saveAll(tasks.stream().map(dto -> taskFactory.from(dto, project)).collect(toList())).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    TaskDto save(TaskDto dtoToSave) {
        Task toSave = taskRepository.save(
                taskRepository.findById(dtoToSave.getId())
                        .map(existingTask -> {
                            if (existingTask.isDone() != dtoToSave.isDone()) {
                                existingTask.setChangesCount(existingTask.getChangesCount() + 1);
                                existingTask.setDone(dtoToSave.isDone());
                            }
                            existingTask.setAdditionalComment(dtoToSave.getAdditionalComment());
                            existingTask.setDeadline(dtoToSave.getDeadline());
                            existingTask.setDescription(dtoToSave.getDescription());
                            return existingTask;
                        }).orElseGet(() -> {
                    var result = new Task(dtoToSave.getDescription(), dtoToSave.getDeadline(), null);
                    result.setAdditionalComment(dtoToSave.getAdditionalComment());
                    return result;
                })
        );
        return toDto(toSave);
    }

    void delete(int id) {
        taskRepository.deleteById(id);
    }

    void initializeData() {
        if (taskQueryRepository.count() == 0) {
            var task = new Task("Example task", ZonedDateTime.now(), null);
            taskRepository.save(task);
        }
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
