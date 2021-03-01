package io.github.mat3e.task;

import io.github.mat3e.project.query.SimpleProjectQueryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class TaskFacade {
    private final TaskRepository taskRepository;
    private final TaskQueryRepository taskQueryRepository;
    private final TaskFactory taskFactory;

    TaskFacade(final TaskFactory taskFactory, final TaskRepository taskRepository, final TaskQueryRepository taskQueryRepository) {
        this.taskFactory = taskFactory;
        this.taskRepository = taskRepository;
        this.taskQueryRepository = taskQueryRepository;
    }

    public List<TaskDto> saveAll(final List<TaskDto> tasks, SimpleProjectQueryDto project) {
        return taskRepository.saveAll(tasks.stream().map(dto -> taskFactory.from(dto, project)).collect(toList())).stream()
                .map(Task::toDto)
                .collect(Collectors.toList());
    }

    public boolean areUndoneTasksWithProjectId(int projectId) {
        return taskQueryRepository.existsByDoneIsFalseAndProject_Id(projectId);
    }

    TaskDto save(TaskDto toSave) {
        return taskRepository.save(
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
        ).toDto();
    }

    List<TaskDto> list() {
        return taskQueryRepository.findAll().stream()
                .map(Task::toDto)
                .collect(toList());
    }

    Optional<TaskDto> get(int id) {
        return taskRepository.findById(id).map(Task::toDto);
    }

    void delete(int id) {
        taskRepository.deleteById(id);
    }
}
