package io.github.mat3e.task.vo;

import java.time.ZonedDateTime;

public class TaskCreator {
    private final TaskSourceId sourceId;
    private final String description;
    private final ZonedDateTime deadline;

    public TaskCreator(final TaskSourceId sourceId, final String description, final ZonedDateTime deadline) {
        this.sourceId = sourceId;
        this.description = description;
        this.deadline = deadline;
    }

    public TaskSourceId getSourceId() {
        return sourceId;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }
}
