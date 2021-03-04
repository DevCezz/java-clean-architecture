package io.github.mat3e.task;

import io.github.mat3e.project.dto.SimpleProject;
import io.github.mat3e.task.dto.TaskDto;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TaskFacade {
    private final TaskRepository taskRepository;
    private final TaskFactory taskFactory;

    TaskFacade(final TaskFactory taskFactory, final TaskRepository taskRepository) {
        this.taskFactory = taskFactory;
        this.taskRepository = taskRepository;
    }

    public List<TaskDto> saveAll(final List<TaskDto> tasks, SimpleProject project) {
        return taskRepository.saveAll(tasks.stream().map(dto -> taskFactory.from(dto, project)).collect(toList())).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    TaskDto save(TaskDto dtoToSave) {
        Task toSave = taskRepository.save(
                taskRepository.findById(dtoToSave.getId())
                        .map(existingTask -> {
                            if (existingTask.getSnapshot().isDone() != dtoToSave.isDone()) {
                                existingTask.toggle();
                            }
                            existingTask.updateInfo(
                                    dtoToSave.getAdditionalComment(),
                                    dtoToSave.getDeadline(),
                                    dtoToSave.getDescription()
                            );
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
