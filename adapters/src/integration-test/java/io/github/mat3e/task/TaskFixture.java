package io.github.mat3e.task;

import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.dto.TaskWithChangesDto;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class TaskFixture {

    public static Set<TaskDto> tasks(int amount) {
        return IntStream.rangeClosed(1, amount)
                .mapToObj(TaskFixture::taskWithId)
                .collect(Collectors.toSet());
    }

    public static TaskDto taskWithId(final int id) {
        return TaskDto.builder()
                .withId(id)
                .withDone(true)
                .withDescription("desc-" + id)
                .build();
    }

    public static Set<TaskWithChangesDto> tasksWithChanges(final int amount) {
        return tasks(amount).stream()
                .map(TaskWithChangesTestDto::from)
                .collect(Collectors.toSet());
    }
}

class TaskWithChangesTestDto implements TaskWithChangesDto {

    private final TaskDto taskDto;

    private TaskWithChangesTestDto(final TaskDto taskDto) {
        this.taskDto = taskDto;
    }

    public static TaskWithChangesDto from(TaskDto taskDto) {
        return new TaskWithChangesTestDto(taskDto);
    }

    @Override
    public int getId() {
        return taskDto.getId();
    }

    @Override
    public String getDescription() {
        return taskDto.getDescription();
    }

    @Override
    public boolean isDone() {
        return taskDto.isDone();
    }

    @Override
    public ZonedDateTime getDeadline() {
        return taskDto.getDeadline();
    }

    @Override
    public int getChangesCount() {
        return 0;
    }
}