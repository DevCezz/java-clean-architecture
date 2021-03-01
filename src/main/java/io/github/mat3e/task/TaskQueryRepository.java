package io.github.mat3e.task;

import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.dto.TaskWithChangesDto;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TaskQueryRepository extends Repository<Task, Integer> {

    int count();

    List<TaskDto> findAllBy();

    boolean existsByDoneIsFalseAndProject_Id(int id);

    List<TaskWithChangesDto> findAllWithChangesBy();
}
