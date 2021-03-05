package io.github.mat3e.task;

import io.github.mat3e.project.dto.SimpleProjectSnapshot;

import java.time.ZonedDateTime;

class TaskSnapshot {

    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    private SimpleProjectSnapshot project;

    TaskSnapshot() {}

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
