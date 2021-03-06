package io.github.mat3e.task;

import io.github.mat3e.DomainEventPublisher;
import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.vo.TaskCreator;
import io.github.mat3e.task.vo.TaskEvent;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TaskFacade {
    private final TaskRepository taskRepository;
    private final TaskFactory taskFactory;
    private final DomainEventPublisher publisher;

    TaskFacade(final TaskFactory taskFactory, final TaskRepository taskRepository, final DomainEventPublisher publisher) {
        this.taskFactory = taskFactory;
        this.taskRepository = taskRepository;
        this.publisher = publisher;
    }

    public List<TaskDto> createTasks(final Set<TaskCreator> tasks) {
        return taskRepository.saveAll(tasks.stream().map(Task::createFrom).collect(toList())).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    TaskDto save(TaskDto dtoToSave) {
        Task toSave = taskRepository.save(
                taskRepository.findById(dtoToSave.getId())
                        .map(existingTask -> {
                            if (existingTask.getSnapshot().isDone() != dtoToSave.isDone()) {
                                publisher.publish(existingTask.toggle());
                            }
                            publisher.publish(existingTask.updateInfo(
                                    dtoToSave.getDescription(),
                                    dtoToSave.getDeadline(),
                                    dtoToSave.getAdditionalComment()
                            ));
                            return existingTask;
                        }).orElseGet(() -> taskFactory.from(dtoToSave))
        );
        return toDto(toSave);
    }

    void delete(int id) {
        taskRepository.findById(id)
                .ifPresent(task -> {
                    taskRepository.deleteById(id);
                    publisher.publish(new TaskEvent(
                            task.getSnapshot().getSourceId(),
                            TaskEvent.State.DELETED,
                            null
                    ));
                });
    }

    private TaskDto toDto(Task task) {
        TaskSnapshot snapshot = task.getSnapshot();
        return TaskDto.builder()
                .withId(snapshot.getId())
                .withDescription(snapshot.getDescription())
                .withDone(snapshot.isDone())
                .withDeadline(snapshot.getDeadline())
                .withAdditionalComment(snapshot.getAdditionalComment())
                .build();
    }
}
