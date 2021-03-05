package io.github.mat3e.task.vo;

import java.time.ZonedDateTime;

public class TaskCreator {
    private final TaskSourceId id;
    private final String description;
    private final ZonedDateTime deadline;

    public TaskCreator(final TaskSourceId id, final String description, final ZonedDateTime deadline) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
    }

    TaskSourceId getId() {
        return id;
    }

    String getDescription() {
        return description;
    }

    ZonedDateTime getDeadline() {
        return deadline;
    }
}
