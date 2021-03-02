package io.github.mat3e.task;

import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.dto.TaskWithChangesDto;

import java.util.List;
import java.util.Optional;

public interface TaskQueryRepository {

    int count();

    Optional<TaskDto> findDtoById(int id);

    List<TaskDto> findAllBy();

    boolean existsByDoneIsFalseAndProject_Id(int id);

    List<TaskWithChangesDto> findAllWithChangesBy();
}
