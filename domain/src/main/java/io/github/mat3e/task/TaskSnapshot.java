package io.github.mat3e.task;

import io.github.mat3e.project.dto.SimpleProjectSnapshot;

import java.time.ZonedDateTime;

class TaskSnapshot {

    private final int id;
    private final String description;
    private final boolean done;
    private final ZonedDateTime deadline;
    private final int changesCount;
    private final String additionalComment;
    private final SimpleProjectSnapshot project;

    TaskSnapshot(final int id, final String description, final boolean done, final ZonedDateTime deadline, final int changesCount, final String additionalComment, final SimpleProjectSnapshot project) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.changesCount = changesCount;
        this.additionalComment = additionalComment;
        this.project = project;
    }

    int getId() {
        return id;
    }

    String getDescription() {
        return description;
    }

    boolean isDone() {
        return done;
    }

    ZonedDateTime getDeadline() {
        return deadline;
    }

    int getChangesCount() {
        return changesCount;
    }

    String getAdditionalComment() {
        return additionalComment;
    }

    SimpleProjectSnapshot getProject() {
        return project;
    }
}
